package com.sjchoi.weather.fragment

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.sjchoi.weather.R
import com.sjchoi.weather.common.WeatherApplication
import com.sjchoi.weather.databinding.FragmentNavermapBinding
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class NaverMapFragment: BaseFragment<FragmentNavermapBinding>(FragmentNavermapBinding::inflate), OnMapReadyCallback {
    private lateinit var naverMap : NaverMap
    private var lat : Double = 0.0
    private var lon : Double = 0.0
    private var address : String = ""
    private val marker = Marker()

    companion object{
        fun newInstance(lat: Double, lon : Double): NaverMapFragment {
            val mapFragment  = NaverMapFragment()

            var bundle = Bundle()
            bundle.putDouble("lat", lat)
            bundle.putDouble("lon", lon)

            mapFragment.arguments=bundle
            return mapFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lat = arguments?.getDouble("lat")!!
        lon = arguments?.getDouble("lon")!!

        val mapFragment = childFragmentManager.findFragmentById(R.id.naverMap) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.naverMap, it).commit()
            }

        with(binding){
            mapSearchView.setOnQueryTextListener(object : OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        val location = mapSearchView.query.toString()

                        var addressList : MutableList<Address>? = null

                        if(!location.isNullOrEmpty()){
                            val geocoder = Geocoder(WeatherApplication.getWeatherApplication().applicationContext)

                            try {
                                addressList = geocoder.getFromLocationName(location, 1)
                            }catch (e:IOException){
                                WeatherApplication.getWeatherApplication().toastMessage(e.toString())
                            }

                            val address = addressList!![0]
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
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(lat, lon))
        map.moveCamera(cameraUpdate)

        markerSetCamPos(marker, naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude)
        marker.map=naverMap

        naverMap.addOnCameraChangeListener { reason, animated ->
            markerSetCamPos(marker, naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude)
        }

        naverMap.addOnCameraIdleListener {
            markerSetCamPos(marker, naverMap.cameraPosition.target.latitude,naverMap.cameraPosition.target.longitude)
            address = getAddress(naverMap.cameraPosition.target.latitude, naverMap.cameraPosition.target.longitude)
            binding.mapSearchView.setQuery(address as CharSequence, false)
        }
    }

    private fun markerSetCamPos(marker : Marker, lat:Double, lon: Double){
        marker.position = LatLng(lat, lon)
    }

    private fun getAddress(lat: Double, lng: Double): String {
        val geoCoder = Geocoder(WeatherApplication.getWeatherApplication().applicationContext, Locale.KOREA)
        val address: ArrayList<Address>
        var addressResult = "주소를 가져 올 수 없습니다."
        try {
            address = geoCoder.getFromLocation(lat, lng, 1) as ArrayList<Address>
            if (address.size > 0) {
                // 주소 받아오기
                val currentLocationAddress = address[0].getAddressLine(0)
                    .toString()
                addressResult = currentLocationAddress
            }

        } catch (e: IOException) {
            WeatherApplication.getWeatherApplication().toastMessage(e.toString())
        }
        return addressResult
    }
}