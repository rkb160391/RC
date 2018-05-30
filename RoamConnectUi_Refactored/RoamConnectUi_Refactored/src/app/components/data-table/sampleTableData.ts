export default {
    "reportType": "advanced",
    "response": {
      "userTemplateId": "businessApplication",
      "reportConfiguration": {
        "configuration": {
          "attributes": [],
          "properties": [
          ],
          "metrics": [
            {
              "name": "operator",
              "displayName": "Operator",
              "id": "101",
              "aggregationType": "count",
              "islink":true
            },
            {
              "name": "timestamp",
              "displayName": "timestamp",
              "id": "105",
              "aggregationType": "count",
              "islink":false
            },
            {
              "name": "inboundMSU",
              "displayName": "Inbound MSU",
              "id": "102",
              "aggregationType": "count",
              "islink":false
            },
            {
              "name": "outboundMSU",
              "displayName": "Outbound MSU",
              "id": "103",
              "aggregationType": "count",
              "islink":false
            },
            {
              "name": "totalMSU",
              "displayName": "Total MSU",
              "id": "104",
              "aggregationType": "count"
            }
          ],
          "name": "Destination Report",
          "type": "Table",
          "subType": "pie",
          "entity": {
            "name": "adv_apps_simulator",
            "displayName": "adv_apps_simulator",
            "id": "adv_apps_simulator"
          },
          "legends": [
            {
              "name": "APMGROUP",
              "displayName": "APMGROUP",
              "id": "APMGROUP",
              "islink":false
            }
          ],
          "yaxistitle": "",
          "reportorder": "1",
          "xaxistitle": "timestamp",
          "spanValue": null
        },
        "filter": {
          "condition": "AND",
          "rules": [
            {
              "field": "SUBELEMENT_NAME",
              "id": "SUBELEMENT_NAME",
              "input": "text",
              "value": "d8f062eb-070c-417b-832b-44b415da716d",
              "type": "string",
              "operator": "equal"
            }
          ]
        },
        "samplingPeriod": "all",
        "intervals": "2017-09-29T14:42:00.000Z/2017-10-05T14:42:00.000Z",
        "pagination": {
          "pagenumber": 1,
          "pagesize": 1440,
          "pagingSpec": null
        },
        "limitSpec": null
      },
      "response": [
        {
          "events": [
            {
              "operator": "Airtel",
              "timestamp": "2017-02-23T10:08:09.000Z",
              "inboundMSU": "50",
              "outboundMSU": "20",
              "totalMSU": "70"
            }
          ]
        },
        {
          "events": [
            {
              "operator": "Vodafone",
              "timestamp": "2017-02-23T10:08:09.000Z",
              "inboundMSU": "50",
              "outboundMSU": "20",
              "totalMSU": "70"
            }
          ]
        },
        {
          "events": [
            {
              "operator": "Idea",
              "timestamp": "2017-02-23T10:08:09.000Z",
              "inboundMSU": "10",
              "outboundMSU": "10",
              "totalMSU": "70"
            }
          ]
        }
      ]
    }
  };