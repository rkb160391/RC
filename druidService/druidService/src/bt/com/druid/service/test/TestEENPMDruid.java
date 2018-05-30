package bt.com.druid.service.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import bt.com.druid.service.dispatcher.RequestDispatcher;
import bt.com.druid.service.query.SelectQuery;
import bt.com.druid.service.query.filters.AndFilter;
import bt.com.druid.service.query.filters.Filter;
import bt.com.druid.service.query.filters.JavaScriptFilter;
import bt.com.druid.service.query.filters.OrFilter;
import bt.com.druid.service.query.filters.PagingSpec;
import bt.com.druid.service.query.filters.SelectorFilter;

public class TestEENPMDruid {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		System.out.println("start main method -- ");

		TestEENPMDruid test1 = new TestEENPMDruid();
		test1.selectQuery();

		System.out.println(" end of main data");

	}

	@SuppressWarnings("deprecation")
	public void selectQuery() {

		PagingSpec pagingSpec = new PagingSpec();
		pagingSpec.setThreshold(100);
		SelectQuery query2 = new SelectQuery(pagingSpec);
		query2.setDataSource("EPC_MME_Raw");
		query2.setLowerInterval(new Date(117, 7, 28, 20, 6, 12));
		query2.setUpperInterval(new Date(117, 7, 29, 20, 6, 12));

		query2.addDimensions("NodeName");
		query2.addDimensions("SubElementID");
		

		query2.addMetrics("128974854");
		query2.addMetrics("117498313");
		query2.addMetrics("117498312");

		AndFilter filter = new AndFilter();

		SelectorFilter selectorFilt = new SelectorFilter();
		selectorFilt.setDimension("NodeName");
		selectorFilt.setValue("MME002LTNm");

		OrFilter orFltr = new OrFilter();

		for (int i = 0; i < query2.getMetrics().size(); i++) {
			JavaScriptFilter javascriptFiltr = new JavaScriptFilter();
			javascriptFiltr.setDimension("" + 117498312);
			javascriptFiltr.setFunction("function(x) { return(x >" + (Integer.MIN_VALUE + 1) + ") }");

			orFltr.getFields().add(javascriptFiltr);
		}

		filter.getFields().add((selectorFilt));
		filter.getFields().add(orFltr);

		query2.setDescending(true);

		query2.setFilter(filter);
		ResultSet rs1 = null;
		try {
			rs1 = new RequestDispatcher().sendRequest(query2);

			System.out.println(rs1);
			System.out.println(rs1.getFetchSize());
			/*
			 * while (rs1.next()) { System.out.println(rs1.getFetchSize()); }
			 */
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
