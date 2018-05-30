package com.bt.dyns.model.common;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Rules implements Serializable{

	private String condition;
	private List<Rule> rules;
	private Rule rule;

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	@Override
	public String toString() {
		return "Rules [condition=" + condition + ", rules=" + rules + ", rule=" + rule + "]";
	}

	
}