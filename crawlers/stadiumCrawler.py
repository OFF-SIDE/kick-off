import json
import requests
import time
import datetime

def postStadium(data):
    url = 'http://localhost:8080/stadium/crawler'  # API 엔드포인트 URL

    # POST 요청 보내기
    response = requests.post(url, data=json.dumps(data), headers={'Content-Type': 'application/json'})

    # 응답 확인
    if response.status_code == 200:  # 성공적인 응답을 받았을 때
        result = response.json()  # JSON 형태로 응답 데이터 가져오기
        print(result)
    else:
        print("API 요청 실패:", response.text)

def getStadiumDataFromPublicApi():
    url = 'http://openapi.seoul.go.kr:8088/4b51536b76726b6438387544446862/json/ListPublicReservationSport/1/700/'

    response = requests.get(url)
    if response.status_code == 200:
        return response.json()
    else:
        return {}

def assertDateValidation(inputDate):
    today = int(datetime.date.today().strftime("%Y-%m-%d").replace("-",""))
    inputDate = int(inputDate.replace("-",""))

    if inputDate < today :
        return False
    return True


def stadiumAppend(sendDataList, dataToInsert):
    l = len(sendDataList)
    isInserted = False
    st = l - 10 if l > 10 else 0

    for i in range(st, l):
        if (sendDataList[i]["stadium"]["x"] == dataToInsert["stadium"]["x"] and sendDataList[i]["stadium"]["y"] == dataToInsert["stadium"]["y"] and sendDataList[i]["stadium"]["category"] == dataToInsert["stadium"]["category"]) or (sendDataList[i]["stadium"]["name"] == dataToInsert["stadium"]["name"] and sendDataList[i]["stadium"]["category"] == dataToInsert["stadium"]["category"]):
            sendDataList[i]["stadiumInfoList"].append(dataToInsert["stadiumInfoList"][0])
            isInserted = True
            break

    if isInserted == False:
        sendDataList.append(dataToInsert)

if __name__ == "__main__":
    LocationEnum = {
        "강남구" : 0, "강동구" : 1, "강북구" : 2, "강서구" : 3, "관악구" : 4,
        "광진구" : 5, "구로구" : 6, "금천구" : 7, "노원구" : 8, "도봉구" : 9,
        "동대문구" : 10, "동작구": 11, "마포구": 12, "서대문구" : 13, "서초구" : 14,
        "성동구" : 15, "성북구" : 16, "송파구" : 17, "양천구" : 18,"영등포구" : 19,
        "용산구": 20, "은평구" : 21, "종로구" : 22, "중구" : 23, "중랑구" : 24
    }
    StadiumCategoryEnum = {
        "테니스장" : 0, "축구장" : 1, "풋살장": 2, "족구장" : 3, "야구장" : 4,
        "농구장" : 5, "배구장" : 6, "배드민턴장" : 7, "골프장" : 8, "다목적경기장" : 8, "교육시설": 8, "체육관":8, "탁구장":8
    }

    data = getStadiumDataFromPublicApi()
    dataLength = data['ListPublicReservationSport']['list_total_count']
    resultData = data['ListPublicReservationSport']['row']
    sendDataList = []

    # 읽어온 데이터 삽입
    for i in range(dataLength):
        try:
            if assertDateValidation(resultData[i]["RCPTENDDT"][:10]):
                body = dict()

                stadium = dict()
                # stadium
                stadium["name"] = resultData[i]["PLACENM"]
                stadium["location"] = LocationEnum[resultData[i]["AREANM"]]
                stadium["contactPhone"] = resultData[i]["TELNO"]
                stadium["comment"] = resultData[i]["DTLCONT"][:1024]
                stadium["category"] = StadiumCategoryEnum[resultData[i]["MINCLASSNM"]]
                stadium["price"] = resultData[i]["PAYATNM"]
                stadium["image"] = resultData[i]["IMGURL"]
                stadium["x"] =  resultData[i]["X"]
                stadium["y"] =  resultData[i]["Y"]
                stadium["openAt"] = resultData[i]["V_MIN"]
                stadium["closeAt"] = resultData[i]["V_MAX"]
                # stadium_info
                stadiumInfoList = dict()
                stadiumInfoList["externalId"] = resultData[i]["SVCID"]
                stadiumInfoList["subName"] = resultData[i]["SVCNM"]
                stadiumInfoList["status"] = True if resultData[i]["SVCSTATNM"] == "접수중" else False
                stadiumInfoList["link"] = resultData[i]["SVCURL"]
                stadiumInfoList["openDay"] = resultData[i]["RCPTBGNDT"]
                stadiumInfoList["closeDay"] = resultData[i]["RCPTENDDT"]

                body["stadium"] = stadium
                body["stadiumInfoList"] = [stadiumInfoList]

                stadiumAppend(sendDataList, body)
        except Exception as e:    # 모든 예외의 에러 메시지를 출력할 때는 Exception을 사용
            print('예외가 발생했습니다.', e)

    postStadium(sendDataList)
