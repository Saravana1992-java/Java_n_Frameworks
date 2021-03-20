package com.learn.free.studios.gbs.model;

import java.util.HashMap;

public class Questionnaire {

	public Question question;
	private HashMap<Integer, Question> questionnaire = null;

	public Questionnaire(Question question) {
		if (questionnaire == null) {
			questionnaire = new HashMap<Integer, Question>();
		}
		int size = questionnaire.size();
		questionnaire.put(size + 1, question);
	}

	public HashMap<Integer, Question> getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(HashMap<Integer, Question> questionnaire) {
		this.questionnaire = questionnaire;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionnaire == null) ? 0 : questionnaire.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Questionnaire other = (Questionnaire) obj;
		if (questionnaire == null) {
			if (other.questionnaire != null)
				return false;
		} else if (!questionnaire.equals(other.questionnaire))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Questionnaire [questionnaire=" + questionnaire + "]";
	}

}
