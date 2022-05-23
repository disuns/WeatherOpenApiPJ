# WeatherOpenApiPJ
날씨 및 생활지수 앱

## **기본 기능**
시작화면(실시간 예보 화면) : GPS/LBS와 네이버맵을 통해 얻어온 주소를 기반으로 실시간 예보, 3일간의 시간예보, 주간예보을 나타낸다   
대기예보 화면 : 주소를 기반으로한 가장 가까운 측정소의 대기예보 및 전국의 미세먼지 예보와 시간별 미세먼지 예측모델을 나타낸다   
주소 검색 화면 : 네이버맵을 이용하여 원하는 지점의 주소를 얻어온다   

## **사용 기술**
OkHttp3 + Retrofit2를 이용하여 OpenAPI의 요청과 응답   
SplashActivity를 통하여 로딩화면 구현( GPS Permission 체크 )   
Material UI/UX 에 기반한 ViewPager2 + TabLayout을 활용한 화면 구현   
RecyclerViewAdapter + ViewPager2 + PageTransform을 활용한 화면구성   
MVVM 기반 개발 (ViewModel, LiveData 및 Repository를 기반한 REST 데이터 관리)   
Glide를 활용한 Image 관리
Spinner와 SpinnerItem을 기반한 Custom Widget 구현

## **화면 구성**
### **로딩 화면**

![KakaoTalk_Photo_2022-05-23-16-08-00 004](https://user-images.githubusercontent.com/97460483/169763141-fd186016-c310-489e-9915-c0de05ff39bf.jpeg)

<img src = "https://user-images.githubusercontent.com/97460483/169763141-fd186016-c310-489e-9915-c0de05ff39bf.jpeg" width = "50% | height = "50%">

### **시작화면**(실시간 예보 화면)

![KakaoTalk_Photo_2022-05-23-16-08-00 003](https://user-images.githubusercontent.com/97460483/169763134-07f5e69c-ccf5-4657-a4e0-5dc708453fb9.jpeg)
                                                                                                                                               
#### 배치
CardView, ViewPager2, RecyclerView 를 기본으로 활용하여 UI/UX 구현

### **대기예보 화면**

![KakaoTalk_Photo_2022-05-23-16-07-59 001](https://user-images.githubusercontent.com/97460483/169763095-4fe9f79b-d802-4a4c-b28b-4f1309d525fc.jpeg)

#### 배치
ScrollView를 이용하여 CardView, ViewPager2, RecyclerView, Spinner 를 활용

### **주소 검색 화면**

![KakaoTalk_Photo_2022-05-23-16-07-59 002](https://user-images.githubusercontent.com/97460483/169763121-12bc8eec-bb82-4b78-acc9-348e5cf1a183.jpeg)

#### 배치
Fragment와 SearchView 를 활용하여 네이버맵 적용

## **작업 중 주요 이슈**

### API 데이터 관리
+ 기존 MVC패턴과 구조로는 주소 변경시 데이터의 즉시 반영의 어려움을 느낌
    + 해결 : MVVM패턴과 LiveData를 활용하는 구조로 리팩토링 하여 모든 데이터를 ViewModel에서 관리하고 데이터의 즉시 반영 가능하도록 변경

### 네이버맵 활용중 주소 이슈
+ 구글 Geocode가 기반이 된 주소는 기존 주소보다 보기 어렵다고 판단
    + 해결 : 네이버 ReverseGeocode를 활용하여 도로명 주소를 받아오고, 도로명 주소가 존재하지 않을 경우 지번 주소를 출력
  
### API요청의 호출 이슈
+ 요청 주소가 바뀔때마다 다수의 REST요청을 동시에 호출
    + Coroutine을 이용하여 순차적 호출을 하도록 코드 변경 후 Progress 화면을 통해 데이터를 받는 동안에는 다른 화면에 영향을 못 주도록 하여 연속된 데이터 호출의 경우를 제한
