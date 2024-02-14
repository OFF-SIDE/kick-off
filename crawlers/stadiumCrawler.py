import json
import requests
import time
import datetime

def postStadium(data):
    url = 'http://localhost:8080/stadium'  # API 엔드포인트 URL

    # POST 요청 보내기
    response = requests.post(url, data=json.dumps(data), headers={'Content-Type': 'application/json'})

    # 응답 확인
    if response.status_code == 200:  # 성공적인 응답을 받았을 때
        result = response.json()  # JSON 형태로 응답 데이터 가져오기
        print(result)
    else:
        print("API 요청 실패:", response.text)

def getStadiumDataFromPublicApi():
    url = 'http://openapi.seoul.go.kr:8088/4b51536b76726b6438387544446862/json/ListPublicReservationSport/1/500/'

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
        if (sendDataList[i]["stadium"]["X"] == dataToInsert["stadium"]["X"] and sendDataList[i]["stadium"]["Y"] == dataToInsert["stadium"]["Y"] and sendDataList[i]["stadium"]["category"] == dataToInsert["stadium"]["category"]) or (sendDataList[i]["stadium"]["name"] == dataToInsert["stadium"]["name"] and sendDataList[i]["stadium"]["category"] == dataToInsert["stadium"]["category"]):
            sendDataList[i]["stadiumInfoList"].append(dataToInsert["stadiumInfoList"][0])
            isInserted = True
            break

    if isInserted == False:
        sendDataList.append(dataToInsert)

if __name__ == "__main__":
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
                stadium["location"] = resultData[i]["AREANM"]
                stadium["contactPhone"] = resultData[i]["TELNO"]
                stadium["comment"] = resultData[i]["DTLCONT"][:1024]
                stadium["category"] = resultData[i]["MINCLASSNM"]
                stadium["price"] = resultData[i]["PAYATNM"]
                stadium["image"] = resultData[i]["IMGURL"]
                stadium["X"] =  resultData[i]["X"]
                stadium["Y"] =  resultData[i]["Y"]
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
