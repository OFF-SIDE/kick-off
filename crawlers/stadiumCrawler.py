import json
import requests
import time
import datetime

def postStadium(data):
    url = 'http://13.209.73.252:8080/stadium'  # API 엔드포인트 URL

    # POST 요청 보내기
    response = requests.post(url, data=json.dumps(data), headers={'Content-Type': 'application/json'})

    # 응답 확인
    if response.status_code == 200:  # 성공적인 응답을 받았을 때
        result = response.json()  # JSON 형태로 응답 데이터 가져오기
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
    print(today)
    inputDate = int(inputDate.replace("-",""))

    if inputDate < today :
        return False
    return True


def stadiumAppend(sendDataList, dataToInsert):
    l = len(sendDataList)
    isInserted = False
    st = l - 10 if l > 10 else 0

    for i in range(st, l):
        if (sendDataList[i]["X"] == dataToInsert["X"] and sendDataList[i]["Y"] == dataToInsert["Y"] and sendDataList[i]["category"] == dataToInsert["category"]) or (sendDataList[i]["name"] == dataToInsert["name"] and sendDataList[i]["category"] == dataToInsert["category"]):
            sendDataList[i]["subName"].append(dataToInsert["subName"][0])
            sendDataList[i]["link"].append(dataToInsert["link"][0])
            sendDataList[i]["externalId"].append(dataToInsert["externalId"][0])
            sendDataList[i]["openDay"].append(dataToInsert["openDay"][0])
            sendDataList[i]["closeDay"].append(dataToInsert["closeDay"][0])
            sendDataList[i]["status"].append(dataToInsert["status"][0])
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
                body["name"] = resultData[i]["PLACENM"]
                body["location"] = resultData[i]["AREANM"]
                body["contactPhone"] = resultData[i]["TELNO"]
                body["subName"] = [resultData[i]["SVCNM"]]
                body["comment"] = resultData[i]["DTLCONT"][:1024]
                body["category"] = resultData[i]["MINCLASSNM"]
                body["price"] = resultData[i]["PAYATNM"]
                body["image"] = resultData[i]["IMGURL"]
                body["link"] = [resultData[i]["SVCURL"]]
                body["X"] =  resultData[i]["X"]
                body["Y"] =  resultData[i]["Y"]
                body["externalId"] = [resultData[i]["SVCID"]]
                body["openAt"] = resultData[i]["V_MIN"]
                body["closeAt"] = resultData[i]["V_MAX"]
                body["openDay"] = resultData[i]["RCPTBGNDT"]
                body["closeDay"] = resultData[i]["RCPTENDDT"]
                body["status"] = resultData[i]["SVCSTATNM"]

                stadiumAppend(sendDataList, body)
        except Exception as e:    # 모든 예외의 에러 메시지를 출력할 때는 Exception을 사용
            print('예외가 발생했습니다.', e)

    print(sendDataList)
