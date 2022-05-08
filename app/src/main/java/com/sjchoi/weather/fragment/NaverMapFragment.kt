package com.sjchoi.weather.fragment

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.sjchoi.weather.R
import com.sjchoi.weather.common.DataConvert
import com.sjchoi.weather.common.NUM0
import com.sjchoi.weather.common.NUM1
import com.sjchoi.weather.common.WeatherApplication
import com.sjchoi.weather.databinding.FragmentNavermapBinding
import com.sjchoi.weather.viewmodel.WeatherViewModel
import java.io.IOException

class NaverMapFragment: BaseFragment<FragmentNavermapBinding>(FragmentNavermapBinding::inflate), OnMapReadyCallback {
    private lateinit var naverMap : NaverMap
    private var lat : Double = 0.0
    private var lon : Double = 0.0
    private var address : String = ""
    private val marker = Marker()

    companion object{
        fun newInstance() = NaverMapFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.naverMap) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.naverMap, it).commit()
            }

        with(binding){
            mapSearchView.setOnQueryTextListener(object : OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        val location = mapSearchView.query.toString()

                        var addressList : MutableList<Address>? = null

                        if(location.isNotEmpty()){
                            val geocoder = Geocoder(weatherApplication.applicationContext)

                            try {
                                addressList = geocoder.getFromLocationName(location, NUM1.toInt())
                            }catch (e:IOException){
                                weatherApplication.toastMessage(e.toString())
                            }

                            val address = addressList!![NUM0.toInt()]
                            markerSetCamPos(marker,address.latitude, address.longitude)

                            naverMap.moveCamera(CameraUpdate.scrollTo(LatLng(address.latitude, address.longitude)))
                        }

                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
            })
        }
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        popBackStack()
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        with(viewModel){
            lat = getLat().value!!
            lon = getLon().value!!
            getAddress().observe(viewLifecycleOwner){
                address = DataConvert.mapAddressConvert(it)
                binding.mapSearchView.setQuery(address as CharSequence, false)
            }
        }
        with(naverMap) {
            //줌, 위경도 제한, 수치 조정 필요
            extent = LatLngBounds(LatLng(31.43, 122.37), LatLng(44.35, 132.0))
            minZoom = 5.0
            maxZoom = 18.0

            val cameraUpdate = CameraUpdate.scrollTo(LatLng(lat, lon))
            moveCamera(cameraUpdate)

            markerSetCamPos(marker, cameraPosition.target.latitude, cameraPosition.target.longitude)
            marker.map = this

            addOnCameraChangeListener { _, _ ->
                markerSetCamPos(
                    marker,
                    cameraPosition.target.latitude,
                    cameraPosition.target.longitude
                )
            }

            addOnCameraIdleListener {
                markerSetCamPos(
                    marker,
                    cameraPosition.target.latitude,
                    cameraPosition.target.longitude
                )
                viewModel.restReGe(cameraPosition.target.latitude, cameraPosition.target.longitude)
                binding.mapSearchView.setQuery(address as CharSequence, false)
            }
        }
    }

    private fun markerSetCamPos(marker : Marker, lat:Double, lon: Double){
        marker.position = LatLng(lat, lon)
    }

    private fun popBackStack(){
        with(viewModel){
            getLat().value = naverMap.cameraPosition.target.latitude
            getLon().value = naverMap.cameraPosition.target.longitude
            convertGRIDGPS(NUM0.toInt())
        }
    }
}