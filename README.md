# 해초롬 Backend Systems

## DIVE 2024 Hackathon

이번 해커톤의 주제는 **바다환경지킴이 빅데이터 구축용 스마트 수거앱 개발**이었습니다. 이 프로젝트는 바다환경지킴이들이 해양쓰레기를 수거하는 과정에서 데이터를 수집하고, 이를 기반으로 효율적인 수거 경로를 계획하며, 수거된 쓰레기의 종류와 양을 관리할 수 있는 시스템을 만드는 것을 목표로 했습니다.
<br>

#### 해커톤에서 개발한 애플리케이션은 사용자의 로그인 정보에 따라 다른 모드를 제공하며, 각각의 모드에서 다양한 기능을 제공합니다.

- **조사 모드**: 현장에서 사진을 촬영하고, 쓰레기의 종류와 예측 수거량을 기록합니다.
- **청소 모드**: 실제로 수거된 쓰레기 양을 기록하고 사진을 업로드합니다.
- **관리자 모드**: 수거된 쓰레기 데이터를 기반으로 시각화하고, 분석 및 다운로드 기능을 제공합니다.
- **수거차량 운전자 모드**: 수거된 쓰레기 양과 이동 경로를 시각화하며, 예상 경로를 수정하고 완료 상태를 기록할 수 있습니다.
<br>

이 앱은 GPS(위도, 경도)를 활용해 쓰레기 조사 및 수거 위치를 기록하고, Retrofit 사용을 통해 데이터를 서버로 전송하여 빅데이터 구축에 기여하는 방식으로 설계되었습니다.

---

## Server Configuration
<img width="920" alt="KakaoTalk_20241006_082825534" src="https://github.com/user-attachments/assets/615bed25-32df-45ca-8c02-b6d1d87df906">

---

이 프로젝트는 **DIVE 2024 해커톤** 기간 동안 개발되었습니다. 이 대회는 **2024년 10월 4일부터 10월 6일까지** 3일 동안 무박으로 진행된 이벤트입니다.

저희 3인의 참가자들은 혁신적인 솔루션을 만들기 위해 집중적으로 협력했으며, 이 프로젝트는 해커톤 기간 동안 COSIMBA팀의 노력의 결과물입니다.

이 대회는 최첨단 기술을 사용하여 실제 문제를 해결하는 것에 중점을 두었으며, 우리는 **Android Studio**, **Retrofit**, **Kakao Map API**를 활용하여 서버 통합이 가능한 지도 기반 솔루션을 개발하였습니다.

---

## Team
|<img src="https://avatars.githubusercontent.com/u/136697128?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/102722507?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/58455389?v=4" width="150" height="150"/>|
|:-:|:-:|:-:|
|eksploiter<br/>Android<br/>[@eksploiter](https://github.com/eksploiter)|dev-9hee<br/>Backend<br/>[@dev-9hee](https://github.com/dev-9hee)|sso9594<br/>Backend<br/>[@sso9594](https://github.com/sso9594)|

