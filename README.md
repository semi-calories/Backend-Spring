# ☘️ SEMI CALORIES - BACKEND


![image](https://github.com/user-attachments/assets/2bfc3314-46ab-41b7-8f30-6715649bfddd)

2023 건국대학교 졸업프로젝트 semi calories - backend 레포지토리

개발기간 : 2023.01 ~ 2023.12 <BR>
유지보수 : 2024.01 ~ 2024.06


## 🥗 프로젝트 소개

세미칼로리는 건국대학교 컴퓨터공학부 졸업 프로젝트를 위해 구성된 팀프로젝트입니다.

2023년 1학기와 2학기 동안, 사용자 맞춤 음식 추천 기능을 제공하는 식단 관리 어플리케이션을 개발하는 프로젝트를 진행하였습니다.


이 프로젝트는 2030 청년층을 대상으로 하여, 건강한 식단 관리에 대한 지식이 부족한 사용자를 위해 편리한 식단 관리 기능을 제공하는 것을 목표로 합니다. 사진 기반의 식사 기록과 개인 맞춤형 음식 추천 기능을 통해 사용자가 더 나은 식습관을 형성하고 유지할 수 있도록 돕습니다.

<br>

### 😶 팀원 소개
| 이름   | 학번                | 역할                            |
|--------|---------------------|---------------------------------|
| 심혜수 | 건국대학교 컴퓨터공학부 18학번 | 앱 리드 개발자                     |
| 이지영 | 건국대학교 컴퓨터공학부 20학번 | DB 및 클라우드 개발자, 백엔드 서브 개발자 |
| 박지은 | 건국대학교 컴퓨터공학부 20학번 | 백엔드 리드 개발자                 |
| 김희진 | 건국대학교 컴퓨터공학부 20학번 | AI 리드 개발자, 백엔드 서브 개발자    |



<br><BR>

## 앱 다운로드 및 시연 영상 링크

- iOS

[‎Semi-Calories](https://apps.apple.com/kr/app/semi-calories/id6471895779)

- Android

[Semi-Calories - Apps on Google Play](https://play.google.com/store/apps/details?id=com.shimhyesu.SemiCalories&pli=1)

- 시연영상 

https://www.youtube.com/watch?si=L2XPrdrjfqKJwl-q&v=tg4nNRUMVk4&feature=youtu.be

<br><BR>

## 🪽 Stacks

- 언어: Java
- 프레임워크: Spring Boot
- ORM: JPA (Java Persistence API)
- 데이터베이스: MySQL
- 클라우드 서비스: AWS (EC2, RDS, S3, CodeDeploy)
- 커뮤니케이션 : Notion

<Br>

## 📜  API Document
https://www.notion.so/semi-calories/API-Document-7097f7b7e3b044848caefc7a9be3bf42


<br>

### 🔎 기능리스트

| **사용자 관리** |  |  |
| --- | --- | --- |
|  | 회원가입 | 이메일, 비밀번호, 이름 |
|  | 로그인&로그아웃 |  |
|  | 탈퇴 |  |
|  | 정보 관리 | 사용자 사진, 목표(감량, 증량, 유지), 신체정보, 활동량, 선호/비선호 음식 |
| **식사 관리** |  |  |
|  | 식사 저장(사진) | 사진 기반 음식 분류 후 1인분 기준 칼로리 제공 → 섭취량에 맞춰 수정 |
|  | 식사 저장(텍스트) | 사용자가 음식 직접 입력 |
|  | 식사 만족도 저장  |  |
|  | 식사 조회 |  |
|  | 식사 수정 |  |
|  | 식사 삭제 |  |
| **음식 추천** |  |  |
|  | 추천탭 추천 | 홈화면 하단 추천탭을 통한 음식 추천 |
|  | 푸시 알림 추천 | 사용자가 등록한 시간에 푸시알림 통한 음식 추천 |
| **식사 통계** |  |  |
|  | 주간 통계 조회 | 1달 기준 주별 사용자 섭취 칼로리 대비 영양소 비율 통계 |
|  | 월간 통계 조회 | 1년 기준 월별 사용자 섭취 칼로리 대비 영양소 비율 통계 |
| **몸무게 관리** |  |  |
|  | 몸무게 저장 |  |
|  | 몸무게 조회 | 기간별 입력된 몸무게 추이 조회 |
|  | 몸무게 수정 |  |
|  | 몸무게 삭제 |  |
|  | 예상몸무게 조회 | 목표 몸무게, 목표 기간을 통해 예상 몸무게 조회 |

<br>


## 🗂️ Architecture

### 1️⃣ SW TOP Level Architecture
![image](https://github.com/user-attachments/assets/a1eb030e-13c4-4cf1-aad6-5ba68e4fa701)

<BR>

### 2️⃣ Total SW
![image](https://github.com/user-attachments/assets/001b8a85-6fcc-42fd-ac17-a18898b20ba2)


<BR>

### 3️⃣ Database
![image](https://github.com/user-attachments/assets/ccfba21b-a529-4b0d-9bba-8cf189956682)

