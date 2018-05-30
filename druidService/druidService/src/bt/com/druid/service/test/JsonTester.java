package bt.com.druid.service.test;

import java.sql.ResultSet;

import bt.com.druid.service.dispatcher.RequestDispatcher;
import bt.com.druid.service.query.QueryConstants;
import bt.com.druid.service.query.SelectQuery;

public class JsonTester {

	public static void main(String[] args) {
		try {
			/*
			 * GroupByQuery query = new GroupByQuery();
			 * query.setDataSource("DSLAM"); query.setGranularity("all");
			 * query.setLowerInterval(QueryConstants.ISO8601Formatter.parse(
			 * "2017-03-09T05:00:00Z"));
			 * query.setUpperInterval(QueryConstants.ISO8601Formatter.parse(
			 * "2017-03-09T06:00:00Z")); query.addAggregations(new
			 * Aggregator("doubleLastExtended", "PMP_DSL_OPR_MODE_LAST",
			 * "PMP_DSL_OPR_MODE")); query.addDimensions("ELEMENT_NAME");
			 * ResultSet rs = new RequestDispatcher().sendRequest(query);
			 * 
			 * query.addAggregations(new Aggregator())
			 * 
			 * SelectQuery query2 = new SelectQuery();
			 * query2.setDataSource("DSLAM");
			 * query2.setLowerInterval(QueryConstants.ISO8601Formatter.parse(
			 * "2017-03-09T05:00:00Z"));
			 * query2.setUpperInterval(QueryConstants.ISO8601Formatter.parse(
			 * "2017-03-09T06:00:00Z")); query2.addDimensions("ELEMENT_NAME");
			 * query2.addMetrics("PMP_DSL_OPR_MODE"); ResultSet rs1 = new
			 * RequestDispatcher().sendRequest(query2); System.out.println(rs1);
		 */
		/*GroupByQuery query = new GroupByQuery();
			query.setDataSource("DSLAM");
			query.addDimensions("ELEMENT_NAME");
			query.addDimensions("SUBELEMENT_NAME");
			query.addDimensions("SUBELEMENT_TYPE");
			query.addDimensions("PMP_ATUR_VDR_ID");
			query.addDimensions("PMP_DSL_PRF_NAME");
			query.addDimensions("PMP_ATUR_SYS_VDR_ID");
			query.addDimensions("PMP_ATUR_VER_NUM");
			query.addDimensions("VENDOR");
			query.setGranularity("all");
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_DSL_OPR_MODE_LAST", "PMP_DSL_OPR_MODE"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_FECS_NER_US_SUM", "PMP_FECS_NER_US"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_FECS_FER_DS_SUM", "PMP_FECS_FER_DS"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_SES_FER_DS_SUM", "PMP_SES_FER_DS"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_ES_NER_US_SUM", "PMP_ES_NER_US"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_SES_NER_US_SUM", "PMP_SES_NER_US"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_ES_FER_DS_SUM", "PMP_ES_FER_DS"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_LINE_RATE_US_LAST", "PMP_LINE_RATE_US"));
			query.addAggregations(new Aggregator("doubleMinExtended", "PMP_LINE_RATE_US_MIN", "PMP_LINE_RATE_US"));
			query.addAggregations(new Aggregator("doubleMaxExtended", "PMP_LINE_RATE_US_MAX", "PMP_LINE_RATE_US"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_LINE_RATE_DS_LAST", "PMP_LINE_RATE_DS"));
			query.addAggregations(new Aggregator("doubleMinExtended", "PMP_LINE_RATE_DS_MIN", "PMP_LINE_RATE_DS"));
			query.addAggregations(new Aggregator("doubleMaxExtended", "PMP_LINE_RATE_DS_MAX", "PMP_LINE_RATE_DS"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_MAX_ATBL_DR_US_LAST", "PMP_MAX_ATBL_DR_US"));
			query.addAggregations(new Aggregator("doubleMinExtended", "PMP_MAX_ATBL_DR_US_MIN", "PMP_MAX_ATBL_DR_US"));
			query.addAggregations(new Aggregator("doubleMaxExtended", "PMP_MAX_ATBL_DR_US_MAX", "PMP_MAX_ATBL_DR_US"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_MAX_ATBL_DR_DS_LAST", "PMP_MAX_ATBL_DR_DS"));
			query.addAggregations(new Aggregator("doubleMinExtended", "PMP_MAX_ATBL_DR_DS_MIN", "PMP_MAX_ATBL_DR_DS"));
			query.addAggregations(new Aggregator("doubleMaxExtended", "PMP_MAX_ATBL_DR_DS_MAX", "PMP_MAX_ATBL_DR_DS"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_UP_TIME_LAST", "PMP_UP_TIME"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_UP_TIME_SUM", "PMP_UP_TIME"));
			query.addAggregations(new Aggregator("unforceRetrainCountExtended", "PMP_UP_TIME_RETRAINS", "PMP_UP_TIME"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_SUC_INIT_SUM", "PMP_SUC_INIT"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_FAIL_FULL_INIT_SUM", "PMP_FAIL_FULL_INIT"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_LINE_ATTN_US_LAST", "PMP_LINE_ATTN_US"));
			query.addAggregations(new Aggregator("doubleMaxExtended", "PMP_LINE_ATTN_US_MAX", "PMP_LINE_ATTN_US"));
			query.addAggregations(new Aggregator("doubleMinExtended", "PMP_LINE_ATTN_US_MIN", "PMP_LINE_ATTN_US"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_LINE_ATTN_DS_LAST", "PMP_LINE_ATTN_DS"));
			query.addAggregations(new Aggregator("doubleMaxExtended", "PMP_LINE_ATTN_DS_MAX", "PMP_LINE_ATTN_DS"));
			query.addAggregations(new Aggregator("doubleMinExtended", "PMP_LINE_ATTN_DS_MIN", "PMP_LINE_ATTN_DS"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_SNR_US_LAST", "PMP_SNR_US"));
			query.addAggregations(new Aggregator("doubleNoiseMinExtended", "PMP_SNR_US_MIN", "PMP_SNR_US"));
			query.addAggregations(new Aggregator("doubleMaxExtended", "PMP_SNR_US_MAX", "PMP_SNR_US"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_SNR_DS_LAST", "PMP_SNR_DS"));
			query.addAggregations(new Aggregator("doubleNoiseMinExtended", "PMP_SNR_DS_MIN", "PMP_SNR_DS"));
			query.addAggregations(new Aggregator("doubleMaxExtended", "PMP_SNR_DS_MAX", "PMP_SNR_DS"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_PWR_STT_LAST", "PMP_PWR_STT"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_AATP_US_LAST", "PMP_AATP_US"));
			query.addAggregations(new Aggregator("doubleMaxExtended", "PMP_AATP_US_MAX", "PMP_AATP_US"));
			query.addAggregations(new Aggregator("doubleMinExtended", "PMP_AATP_US_MIN", "PMP_AATP_US"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_AATP_DS_LAST", "PMP_AATP_DS"));
			query.addAggregations(new Aggregator("doubleMaxExtended", "PMP_AATP_DS_MAX", "PMP_AATP_DS"));
			query.addAggregations(new Aggregator("doubleMinExtended", "PMP_AATP_DS_MIN", "PMP_AATP_DS"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_R_US_LAST", "PMP_R_US"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_R_DS_LAST", "PMP_R_DS"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_S_US_LAST", "PMP_S_US"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_S_DS_LAST", "PMP_S_DS"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_D_US_LAST", "PMP_D_US"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_D_DS_LAST", "PMP_D_DS"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_N_US_LAST", "PMP_N_US"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_N_DS_LAST", "PMP_N_DS"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_ACT_INP_US_LAST", "PMP_ACT_INP_US"));
			query.addAggregations(new Aggregator("doubleLastExtended", "PMP_ACT_INP_DS_LAST", "PMP_ACT_INP_DS"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_INMINPEQ_US_SUM", "PMP_INMINPEQ_US"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_INMINPEQ_DS_SUM", "PMP_INMINPEQ_DS"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_INMIAT_US_SUM", "PMP_INMIAT_US"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_INMIAT_DS_SUM", "PMP_INMIAT_DS"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_INMME_US_SUM", "PMP_INMME_US"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_INMME_DS_SUM", "PMP_INMME_DS"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_SES_NER_DS_SUM", "PMP_SES_NER_DS"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_ES_NER_DS_SUM", "PMP_ES_NER_DS"));
			query.addAggregations(new Aggregator("doubleSumExtended", "PMP_FECS_NER_DS_SUM", "PMP_FECS_NER_DS"));
			query.setLowerInterval(QueryConstants.ISO8601Formatter.parse("2017-03-09T06:00:00Z"));
			query.setUpperInterval(QueryConstants.ISO8601Formatter.parse("2017-03-09T07:00:00Z"));
			ResultSet rs = new RequestDispatcher().sendRequest(query);
			rs.last();
			System.out.println(rs.getRow()); */

			/*SelectQuery query2 = new SelectQuery();
			query2.setDataSource("DSLAM");
			query2.setLowerInterval(QueryConstants.ISO8601Formatter.parse("2017-03-09T06:00:00Z"));
			query2.setUpperInterval(QueryConstants.ISO8601Formatter.parse("2017-03-09T07:00:00Z"));
			query2.addDimensions("ELEMENT_NAME");
			query2.addMetrics("PMP_DSL_OPR_MODE");
			ResultSet rs1 = new RequestDispatcher().sendRequest(query2);
			System.out.println(rs1); */
			
/*			SelectQuery selectQuery = new SelectQuery();

            selectQuery.setDataSource("IL2S");

            AndFilter andFilter = new AndFilter();

            andFilter.addField(new SelectorFilter("ELEMENT_NAME", "BAADPG"));

            andFilter.addField(new SelectorFilter("SUBELEMENT_NAME", "/shelf=0/slot=1/port=4/vlan=2/cvlan=84"));

            selectQuery.setFilter(andFilter);

            selectQuery.setGranularity("hour");

            

            selectQuery.addMetrics("PMP_DISCARD_DS");

            selectQuery.addMetrics("PMP_CRC_EC_FAR_DS");

            selectQuery.addMetrics("PMP_ES_NER_US");

            selectQuery.addMetrics("PMP_TRAN_FRMS");

            selectQuery.addMetrics("PMP_RECV_FRMS");

            selectQuery.addMetrics("PMP_SES_FAR_DS");

            selectQuery.addMetrics("PMP_UAS_NER_US");

            selectQuery.addMetrics("PMP_FECS_NER_US");

        

                    selectQuery.setLowerInterval(QueryConstants.ISO8601Formatter.parse("2017-03-15T00:00:00Z"));

                    selectQuery.setUpperInterval(QueryConstants.ISO8601Formatter.parse("2017-03-15T23:59:59Z"));
                    
             RequestDispatcher dispatcher = new RequestDispatcher("http://10.52.208.91:8082/druid/v2/?pretty",true);
             dispatcher.sendRequest(selectQuery);*/


		/*	SQLQuery sqlQuery = new SQLQuery("SELECT DISTINCT ELEMENT_NAME,SUBELEMENT_NAME FROM DSLAM");
			long startTime = System.currentTimeMillis();
			RequestDispatcher dispatcher = new RequestDispatcher("http://172.25.182.156:8082/druid/v2/sql/",true);
			ResultSet set = dispatcher.sendRequest(sqlQuery);
			set.last();
			System.out.println(set.getString(1));
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(totalTime);*/
			
			
			
			/*SelectQuery query2 = new SelectQuery();
			query2.setDataSource("EENPM");
			query2.setLowerInterval(new Date("2017-08-26T06:00:00Z"));
			query2.setUpperInterval(("2017-08-28T07:00:00Z"));
			query2.addDimensions("ELEMENT_NAME");
			query2.addMetrics("PMP_DSL_OPR_MODE");
			ResultSet rs1 = new RequestDispatcher().sendRequest(query2);
			System.out.println(rs1); */
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
