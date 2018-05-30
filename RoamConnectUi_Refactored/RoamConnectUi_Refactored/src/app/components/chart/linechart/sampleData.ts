export default {
  "userDetails": {
      "id": 123456789,
      "userName": "BT",
      "carrierName": "BT",
      "companyName": "BT",
      "displayName": "BT"
  },
  "dashboardReports": [
      {
          "id": "0EDvbL",
          "name": "ROAM CONNECT",
          "type": null,
          "viewType": null,
          "config": null,
          "reports": [
              {
                  "reportType": "DestinationReport",
                  "response": {
                      "userTemplateId": null,
                      "reportConfiguration": {
                          "configuration": {
                              "attributes": null,
                              "properties": [
                                  {
                                      "name": "INBOUND",
                                      "displayName": "Inbound MSU",
                                      "type": "",
                                      "refrenceField": "INBOUND_count",
                                      "level": 0,
                                      "subHeaders": [
                                          {
                                              "name": "CamelINBOUND",
                                              "displayName": "Camel",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "INBOUND_countcamel",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "DiameterINBOUND",
                                              "displayName": "Diameter",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "INBOUND_countdiameter",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "GSMINBOUND",
                                              "displayName": "GSM",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "INBOUND_countgsm",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          }
                                      ],
                                      "link": false
                                  },
                                  {
                                      "name": "OUTBOUND",
                                      "displayName": "Outbound MSU",
                                      "type": "",
                                      "refrenceField": "OUTBOUND_count",
                                      "level": 0,
                                      "subHeaders": [
                                          {
                                              "name": "CamelOUTBOUND",
                                              "displayName": "Camel",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "OUTBOUND_countcamel",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "DiameterOUTBOUND",
                                              "displayName": "Diameter",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "OUTBOUND_countdiameter",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "GSMOUTBOUND",
                                              "displayName": "GSM",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "OUTBOUND_countgsm",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          }
                                      ],
                                      "link": false
                                  },
                                  {
                                      "name": "TOTALMSU",
                                      "displayName": "Total MSU",
                                      "type": "OUTBOUND_count+INBOUND_count",
                                      "refrenceField": "",
                                      "level": 0,
                                      "subHeaders": [
                                          {
                                              "name": "CamelTOTALMSU",
                                              "displayName": "Camel",
                                              "id": null,
                                              "type": "OUTBOUND_countcamel+INBOUND_countcamel",
                                              "refrenceField": null,
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "DiameterTOTALMSU",
                                              "displayName": "Diameter",
                                              "id": null,
                                              "type": "OUTBOUND_countdiameter+INBOUND_countdiameter",
                                              "refrenceField": null,
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "GSMTOTALMSU",
                                              "displayName": "GSM",
                                              "id": null,
                                              "type": "OUTBOUND_countgsm+INBOUND_countgsm",
                                              "refrenceField": null,
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          }
                                      ],
                                      "link": false
                                  }
                              ],
                              "metrics": [],
                              "name": "Destination Report",
                              "type": "table",
                              "subType": "table",
                              "entity": null,
                              "legends": [
                                  {
                                      "name": "operatorName",
                                      "displayName": "Operator",
                                      "id": null,
                                      "type": "",
                                      "refrenceField": "operatorName",
                                      "level": 3,
                                      "subHeaders": [
                                          {
                                              "name": "operatorName",
                                              "displayName": "Operator",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "operatorName",
                                              "level": 0,
                                              "subHeaders": null,
                                              "link": false
                                          }
                                      ],
                                      "link": false
                                  },
                                  {
                                      "name": "timeStamp",
                                      "displayName": "TimeStamp",
                                      "id": null,
                                      "type": "",
                                      "refrenceField": "timeStamp",
                                      "level": 3,
                                      "subHeaders": [
                                          {
                                              "name": "timeStamp",
                                              "displayName": "TimeStamp",
                                              "id": null,
                                              "type": "",
                                              "refrenceField": "timeStamp",
                                              "level": 0,
                                              "subHeaders": null,
                                              "link": false
                                          }
                                      ],
                                      "link": false
                                  },
                                  {
                                      "name": "Avg Total MSU",
                                      "displayName": "Avg Total MSU",
                                      "id": null,
                                      "type": "",
                                      "refrenceField": "AvgMSU",
                                      "level": 3,
                                      "subHeaders": [
                                          {
                                              "name": "Avg Total MSU",
                                              "displayName": "Avg Total MSU",
                                              "id": null,
                                              "type": "",
                                              "refrenceField": "AvgMSU",
                                              "level": 0,
                                              "subHeaders": null,
                                              "link": false
                                          }
                                      ],
                                      "link": false
                                  },
                                  {
                                      "name": "Peak Total MSU",
                                      "displayName": "Peak Total MSU",
                                      "id": null,
                                      "type": "",
                                      "refrenceField": "peakMSU",
                                      "level": 3,
                                      "subHeaders": [
                                          {
                                              "name": "Peak Total MSU",
                                              "displayName": "Peak Total MSU",
                                              "id": null,
                                              "type": "",
                                              "refrenceField": "peakMSU",
                                              "level": 0,
                                              "subHeaders": null,
                                              "link": false
                                          }
                                      ],
                                      "link": false
                                  }
                              ],
                              "context": null
                          },
                          "filter": null,
                          "samplingPeriod": "15 minutes",
                          "intervals": "last 2 years",
                          "pagination": {
                              "pagenumber": 1,
                              "pagesize": 100000,
                              "pagingSpec": null
                          },
                          "granularity": null,
                          "level": 2,
                          "datasource": null,
                          "totalRecords": 0,
                          "reportType": null
                      },
                      "response": null,
                      "reports": [
                          {
                              "timeStamp": "2018-01-01T03:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T03:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T03:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T03:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T03:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T03:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "P4 (26006), Poland",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "P4 (26006), Poland",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T12:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T12:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T12:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T12:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          }
                      ],
                      "headers": null
                  }
              }
          ]
      },
      {
          "id": "0EDvbL",
          "name": "ROAM CONNECT",
          "type": null,
          "viewType": null,
          "config": null,
          "reports": [
              {
                  "reportType": "DestinationReport",
                  "response": {
                      "userTemplateId": null,
                      "reportConfiguration": {
                          "configuration": {
                              "attributes": null,
                              "properties": [
                                  {
                                      "name": "INBOUND",
                                      "displayName": "Inbound MSU",
                                      "type": "",
                                      "refrenceField": "INBOUND_count",
                                      "level": 0,
                                      "subHeaders": [
                                          {
                                              "name": "CamelINBOUND",
                                              "displayName": "Camel",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "INBOUND_countcamel",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "DiameterINBOUND",
                                              "displayName": "Diameter",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "INBOUND_countdiameter",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "GSMINBOUND",
                                              "displayName": "GSM",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "INBOUND_countgsm",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          }
                                      ],
                                      "link": false
                                  },
                                  {
                                      "name": "OUTBOUND",
                                      "displayName": "Outbound MSU",
                                      "type": "",
                                      "refrenceField": "OUTBOUND_count",
                                      "level": 0,
                                      "subHeaders": [
                                          {
                                              "name": "CamelOUTBOUND",
                                              "displayName": "Camel",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "OUTBOUND_countcamel",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "DiameterOUTBOUND",
                                              "displayName": "Diameter",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "OUTBOUND_countdiameter",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "GSMOUTBOUND",
                                              "displayName": "GSM",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "OUTBOUND_countgsm",
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          }
                                      ],
                                      "link": false
                                  },
                                  {
                                      "name": "TOTALMSU",
                                      "displayName": "Total MSU",
                                      "type": "OUTBOUND_count+INBOUND_count",
                                      "refrenceField": "",
                                      "level": 0,
                                      "subHeaders": [
                                          {
                                              "name": "CamelTOTALMSU",
                                              "displayName": "Camel",
                                              "id": null,
                                              "type": "OUTBOUND_countcamel+INBOUND_countcamel",
                                              "refrenceField": null,
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "DiameterTOTALMSU",
                                              "displayName": "Diameter",
                                              "id": null,
                                              "type": "OUTBOUND_countdiameter+INBOUND_countdiameter",
                                              "refrenceField": null,
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          },
                                          {
                                              "name": "GSMTOTALMSU",
                                              "displayName": "GSM",
                                              "id": null,
                                              "type": "OUTBOUND_countgsm+INBOUND_countgsm",
                                              "refrenceField": null,
                                              "level": 3,
                                              "subHeaders": null,
                                              "link": true
                                          }
                                      ],
                                      "link": false
                                  }
                              ],
                              "metrics": [],
                              "name": "Destination Report",
                              "type": "table",
                              "subType": "table",
                              "entity": null,
                              "legends": [
                                  {
                                      "name": "operatorName",
                                      "displayName": "Operator",
                                      "id": null,
                                      "type": "",
                                      "refrenceField": "operatorName",
                                      "level": 3,
                                      "subHeaders": [
                                          {
                                              "name": "operatorName",
                                              "displayName": "Operator",
                                              "id": null,
                                              "type": null,
                                              "refrenceField": "operatorName",
                                              "level": 0,
                                              "subHeaders": null,
                                              "link": false
                                          }
                                      ],
                                      "link": false
                                  },
                                  {
                                      "name": "timeStamp",
                                      "displayName": "TimeStamp",
                                      "id": null,
                                      "type": "",
                                      "refrenceField": "timeStamp",
                                      "level": 3,
                                      "subHeaders": [
                                          {
                                              "name": "timeStamp",
                                              "displayName": "TimeStamp",
                                              "id": null,
                                              "type": "",
                                              "refrenceField": "timeStamp",
                                              "level": 0,
                                              "subHeaders": null,
                                              "link": false
                                          }
                                      ],
                                      "link": false
                                  },
                                  {
                                      "name": "Avg Total MSU",
                                      "displayName": "Avg Total MSU",
                                      "id": null,
                                      "type": "",
                                      "refrenceField": "AvgMSU",
                                      "level": 3,
                                      "subHeaders": [
                                          {
                                              "name": "Avg Total MSU",
                                              "displayName": "Avg Total MSU",
                                              "id": null,
                                              "type": "",
                                              "refrenceField": "AvgMSU",
                                              "level": 0,
                                              "subHeaders": null,
                                              "link": false
                                          }
                                      ],
                                      "link": false
                                  },
                                  {
                                      "name": "Peak Total MSU",
                                      "displayName": "Peak Total MSU",
                                      "id": null,
                                      "type": "",
                                      "refrenceField": "peakMSU",
                                      "level": 3,
                                      "subHeaders": [
                                          {
                                              "name": "Peak Total MSU",
                                              "displayName": "Peak Total MSU",
                                              "id": null,
                                              "type": "",
                                              "refrenceField": "peakMSU",
                                              "level": 0,
                                              "subHeaders": null,
                                              "link": false
                                          }
                                      ],
                                      "link": false
                                  }
                              ],
                              "context": null
                          },
                          "filter": null,
                          "samplingPeriod": "15 minutes",
                          "intervals": "last 2 years",
                          "pagination": {
                              "pagenumber": 1,
                              "pagesize": 100000,
                              "pagingSpec": null
                          },
                          "granularity": null,
                          "level": 2,
                          "datasource": null,
                          "totalRecords": 0,
                          "reportType": null
                      },
                      "response": null,
                      "reports": [
                          {
                              "timeStamp": "2018-01-01T03:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T03:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T03:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T03:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T03:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T03:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T00:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T10:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T04:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "P4 (26006), Poland",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "P4 (26006), Poland",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T08:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T05:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T01:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T11:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T12:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T12:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T07:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:15:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T09:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T12:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T12:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:00:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T06:30:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "P4 (26006), Poland",
                              "product": "2G/3G",
                              "dataList": null
                          },
                          {
                              "timeStamp": "2018-01-01T02:45:00.000Z",
                              "data": {
                                  "INBOUND_count": 1
                              },
                              "operatorName": "2018-01-01T00:00:00.000Z",
                              "product": "2G/3G",
                              "dataList": null
                          }
                      ],
                      "headers": null
                  }
              }
          ]
      }
  ]
}
// export default 
//       {
//         "reportType": "",
//         "response": {
//           "userTemplateId": null,
//           "reportConfiguration": {
//             "configuration": {
//               "attributes": null,
//               "properties": [
//                 {
//                   "name": "INBOUND",
//                   "displayName": "INBOUND",
//                   "type": "",
//                   "refrenceField": "INBOUND_count",
//                   "level": 0,
//                   "subHeaders": [
//                     {
//                       "name": "INBOUND",
//                       "displayName": "INBOUND",
//                       "id": null,
//                       "type": "",
//                       "refrenceField": "INBOUND_count",
//                       "level": 0,
//                       "subHeaders": null,
//                       "link": false
//                     }
//                   ],
//                   "link": false
//                 },
//                 {
//                   "name": "OUTBOUND",
//                   "displayName": "OUTBOUND",
//                   "type": "",
//                   "refrenceField": "OUTBOUND_count",
//                   "level": 0,
//                   "subHeaders": [
//                     {
//                       "name": "OUTBOUND",
//                       "displayName": "OUTBOUND",
//                       "id": null,
//                       "type": "",
//                       "refrenceField": "OUTBOUND_count",
//                       "level": 0,
//                       "subHeaders": null,
//                       "link": false
//                     }
//                   ],
//                   "link": false
//                 },
//                 {
//                   "name": "TOTALMSU",
//                   "displayName": "TOTALMSU",
//                   "type": "OUTBOUND_count+INBOUND_count",
//                   "refrenceField": "",
//                   "level": 0,
//                   "subHeaders": [
//                     {
//                       "name": "TOTALMSU",
//                       "displayName": "TOTALMSU",
//                       "id": null,
//                       "type": "OUTBOUND_count+INBOUND_count",
//                       "refrenceField": "",
//                       "level": 0,
//                       "subHeaders": null,
//                       "link": false
//                     }
//                   ],
//                   "link": false
//                 }
//               ],
//               "metrics": [],
//               "name": "Destination Report",
//               "type": "graph",
//               "subType": "line",
//               "entity": null,
//               "legends": [
//                 {
//                   "name": "operatorName",
//                   "displayName": "operatorName",
//                   "id": null,
//                   "type": "",
//                   "refrenceField": "operatorName",
//                   "level": 2,
//                   "subHeaders": [
//                     {
//                       "name": "operatorName",
//                       "displayName": "operatorName",
//                       "id": null,
//                       "type": null,
//                       "refrenceField": "operatorName",
//                       "level": 0,
//                       "subHeaders": null,
//                       "link": true
//                     }
//                   ],
//                   "link": true
//                 },
//                 {
//                   "name": "timeStamp",
//                   "displayName": "timeStamp",
//                   "id": null,
//                   "type": "",
//                   "refrenceField": "timeStamp",
//                   "level": 0,
//                   "subHeaders": [
//                     {
//                       "name": "timeStamp",
//                       "displayName": "timeStamp",
//                       "id": null,
//                       "type": "",
//                       "refrenceField": "timeStamp",
//                       "level": 0,
//                       "subHeaders": null,
//                       "link": false
//                     }
//                   ],
//                   "link": false
//                 }
//               ],
//               "context": null
//             },
//             "filter": null,
//             "samplingPeriod": "15 minutes",
//             "intervals": "last 2 years",
//             "pagination": {
//               "pagenumber": 1,
//               "pagesize": 100000,
//               "pagingSpec": null
//             },
//             "granularity": null,
//             "level": 1,
//             "datasource": null,
//             "totalRecords": 0
//           },
//           "response": null,
//           "reports": [
//             {
//               "timeStamp": "2017-02-23T10:00:00.000Z",
//               "data": {
//                 "OUTBOUND_count": 30
//               },
//               "operatorName": "WOM (73009), Chile"
//             }
//           ],
//           "headers": null
//         }
//       }
