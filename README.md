# WeatherOpenApiPJ
날씨 및 생활지수 앱

## **기본 기능**
시작화면(실시간 예보 화면) : Gps와 네이버맵을 통해 얻어온 주소를 기반으로 실시간 예보, 3일간의 시간예보, 주간예보을 나타낸다   
대기예보 화면 : 주소를 기반으로한 가장 가까운 측정소의 대기예보 및 전국의 미세먼지 예보와 시간별 미세먼지 예측모델을 나타낸다   
주소 검색 화면 : 네이버맵을 이용하여 원하는 지점의 주소를 얻어온다   

## **사용 기술**
OkHttp3 + Retrofit2를 이용하여 OpenAPI의 요청과 응답   
SplashActivity를 통하여 로딩화면 구현( GPS Permission 체크 )   
ViewPager2 + TabLayout을 활용한 화면 표현   
ViewModel 과 LiveData를 이용한 데이터 관리   
Progress 화면의 경우를 구현하여 데이터를 받는도중 다른 활동 불가   
RecyclerViewAdapter + ViewPager2 + PageTransform을 활용한 화면구성   
Glide를 활용한 ImageURL 표현   
Spinner와 SpinnerItem의 간단한 Custom   

## **화면 구성**

### **시작화면**(실시간 예보 화면)

![KakaoTalk_Snapshot_20220513_164432](https://user-images.githubusercontent.com/97460483/168236623-554be461-16c6-43bb-8818-ef300a2a0234.png)

#### 배치
LinearLayout을 이용하여 ImageView, TextView, CardView, ViewPager2, RecyclerView 를 활용

### **대기예보 화면**

![KakaoTalk_Snapshot_20220513_164506](https://user-images.githubusercontent.com/97460483/168236625-4f71ed78-1cdb-4a74-bd0f-28c7bc57f65b.png)

#### 배치
ScrollView, LinearLayout을 이용하여 ImageView, TextView, CardView, ViewPager2, RecyclerView, Spinner 를 활용

### **주소 검색 화면**

![KakaoTalk_Snapshot_20220513_164537](https://user-images.githubusercontent.com/97460483/168236616-5042f946-fd47-4ed0-9516-4af6073cc7cf.png)

#### 배치
LinearLayout을 이용하여 Fragment와 SearchView 를 활용하여 네이버맵 적용

## **작업 중 주요 이슈**

### API 데이터 관리
+ 기존 MVC패턴과 구조로는 주소 변경시 데이터의 즉시 반영의 어려움을 느낌
    + 해결 : MVVM패턴과 LiveData를 활용하는 구조로 리팩토링 하여 모든 데이터를 ViewModel에서 관리하고 데이터의 즉시 반영 가능하도록 변경

### 네이버맵 활용중 주소 이슈
+ 구글 Geocode가 기반이 된 주소는 기존 주소보다 보기 어렵다고 판단
    + 해결 : 네이버 ReverseGeocode를 활용하여 도로명 주소를 받아오고, 도로명 주소가 존재하지 않을 경우 지번 주소를 출력
  
### API요청의 호출 이슈
+ 요청 주소가 바뀔때마다 다수의 Rest요청을 동시에 호출
    + Coroutine을 이용하여 순차적 호출을 하도록 구조 변경 후 Progress 화면을 통해 데이터를 받는 동안에는 다른 화면에 영향을 못 주도록 하여 연속된 데이터 호출의 경우를 제한
