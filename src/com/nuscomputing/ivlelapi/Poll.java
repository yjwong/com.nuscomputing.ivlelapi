package com.nuscomputing.ivlelapi;

import java.util.List;
import java.util.Map;

/**
 * Represents a poll.
 * @author Wong Yong Jie
 */
public class Poll extends IVLEObject {
	// {{{ properties
	
	/** Does this poll allow viewing of results? */
	public final Boolean allowViewResult;
	
	/** Does this poll allow multiple voting? */
	public final Boolean allowVoteMultiple;
	
	/** Creator */
	public final User creator;
	
	/** Description */
	public final String description;
	
	/** Is the poll published? */
	public final Boolean published;
	
	/** Title */
	public final String title;
	
	/** Save the map somewhere, because we'll use it */
	private final Map<?, ?> map;
	
	// }}}
	// {{{ methods
	
	/**
	 * Class constructor.
	 */
	Poll(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.map = map;
		this.allowViewResult = extractBool("AllowViewResult", map);
		this.allowVoteMultiple = extractBool("AllowVoteMultiple", map);
		this.creator = new User(this.ivle, (Map<?, ?>) map.get("Creator"));
		this.description = extractString("Description", map);
		this.ID = extractString("ID", map);
		this.published = extractBool("Published", map);
		this.title = extractString("Title", map);
	}

	/**
	 * Method: getQuestions
	 * <p>
	 * Obtains the list of questions in this poll.
	 * 
	 * @return Poll.Question[]
	 */
	public Poll.Question[] getQuestions() {
		// Get the list of questions.
		List<?> questionList = (List<?>) this.map.get("Questions");
		Poll.Question[] q = new Poll.Question[questionList.size()];
		
		// Create an array of Poll.Question from the list.
		for (int i = 0; i < questionList.size(); i++) {
			q[i] = new Poll.Question(this.ivle, (Map<?, ?>) questionList.get(i), this);
		}
		
		return q;
	}
	
	/**
	 * Method: getVotedUser
	 * Get details (ID, name and email) of the users who voted the option.
	 * 
	 * @param pollQuestion
	 * @param pollQuestionOption
	 * @return User[]
	 */
	public User[] getVotedUser(Poll.Question pollQuestion, Poll.QuestionOption pollQuestionOption) throws Exception {
		// Check for sanity.
//		if (pollQuestion == null || pollQuestionOption == null) {
//			throw new IllegalArgumentException();
//		}
//		
//		// Prepare the request.
//		Map<String, String> urlParams = new HashMap<String, String>();
//		urlParams.put("PollID", this.ID);
//		urlParams.put("PollQuestionID", pollQuestion.ID);
//		urlParams.put("PollQuestionOptionId", pollQuestionOption.ID);
//		URL url = IVLE.prepareURL(this.ivle, "Poll_GetVotedUser", urlParams);
//		Request request = new Request(url);
//		
//		// Execute the request.
//		Document document = request.execute().data;
//		XPath xpath = XPathFactory.newInstance().newXPath();
//		NodeList userList = (NodeList) xpath.evaluate("/*/Results/Data_User", document, XPathConstants.NODESET);
//		
//		// Run through the moduleList, generating a new Module each iteration.
//		User[] u = new User[userList.getLength()];
//		for (int i = 0; i < userList.getLength(); i++) {
//			u[i] = new User(this.ivle, userList.item(i));
//		}
//		
//		return u;
		return null;
	}
	
	/**
	 * Method: getVotedUserOther
	 * Gets details (ID, name and email) of the users who voted the 'Others'
	 * option.
	 * 
	 * @param pollQuestion
	 * @return User[]
	 */
	public User[] getVotedUserOther(Poll.Question pollQuestion) throws Exception {
		// Check for sanity.
//		if (pollQuestion == null) {
//			throw new IllegalArgumentException();
//		}
//		
//		// Prepare the request.
//		Map<String, String> urlParams = new HashMap<String, String>();
//		urlParams.put("PollID", this.ID);
//		urlParams.put("PollQuestionID", pollQuestion.ID);
//		URL url = IVLE.prepareURL(this.ivle, "Poll_GetVotedUser_Other", urlParams);
//		Request request = new Request(url);
//		
//		// Execute the request.
//		Document document = request.execute().data;
//		XPath xpath = XPathFactory.newInstance().newXPath();
//		NodeList userList = (NodeList) xpath.evaluate("/*/Results/Data_User", document, XPathConstants.NODESET);
//		
//		// Run through the moduleList, generating a new Module each iteration.
//		User[] u = new User[userList.getLength()];
//		for (int i = 0; i < userList.getLength(); i++) {
//			u[i] = new User(this.ivle, userList.item(i));
//		}
//		
//		return u;
		return null;
	}
	
	/**
	 * Method: submitVote
	 * Votes for a particular option on the poll.
	 * XXX: Not implemented yet.
	 * 
	 * @param pollQuestion
	 * @param pollQuestionOption
	 * @return boolean
	 */
	public boolean submitVote(Poll.Question pollQuestion, Poll.QuestionOption pollQuestionOption) {
		throw new UnsupportedOperationException();
	}
	
	// }}}
	// {{{ classes
	
	/**
	 * Represents a poll question.
	 * @author Wong Yong Jie
	 */
	public class Question extends IVLEObject {
		// {{{ properties
		
		/** Has other option */
		public final Boolean hasOtherOption;
		
		/** ID */
		public final String ID;
		
		/** Poll ID */
		public final Poll poll;
		
		/** Order */
		public final Integer order;
		
		/** Question text */
		public final String questionText;
		
		/** Voted */
		public final Boolean voted;

		/** Save the map somewhere, because we'll use it */
		private final Map<?, ?> map;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		Question() {
			throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
		}
		
		Question(IVLE ivle, Map<?, ?> map, Poll poll) {
			// Set our IVLE object.
			this.ivle = ivle;
			
			// Read data from JSON.
			this.map = map;
			this.poll = poll;
			this.hasOtherOption = extractBool("HasOtherOption", map);
			this.ID = extractString("ID", map);
			this.order = extractInt("Order", map);
			this.questionText = extractString("QuestionText", map);
			this.voted = extractBool("Voted", map);
		}
		
		/**
		 * Method: getQuestionOptions
		 * <p>
		 * Obtains the options for the poll.
		 * 
		 * @return Poll.QuestionOption[]
		 */
		public Poll.QuestionOption[] getQuestionOptions() {
			// Get the list of questions.
			List<?> questionOptionList = (List<?>) this.map.get("QuestionOptions");
			Poll.QuestionOption[] q = new Poll.QuestionOption[questionOptionList.size()];
			
			// Create an array of Poll.Question from the list.
			for (int i = 0; i < questionOptionList.size(); i++) {
				q[i] = new Poll.QuestionOption(this.ivle, (Map<?, ?>) questionOptionList.get(i), poll, this);
			}
			
			return q;
		}
		
		/**
		 * Method: getVotedUser
		 * Get details (ID, name and email) of the users who voted the option.
		 * 
		 * @param pollQuestionOption
		 * @return User[]
		 */
		public User[] getVotedUser(Poll.QuestionOption pollQuestionOption) throws Exception {
			return this.poll.getVotedUser(this, pollQuestionOption);
		}
		
		/**
		 * Method: submitVote
		 * Votes for a particular option on the poll.
		 * XXX: Not implemented yet.
		 * 
		 * @param pollQuestionOption
		 * @return boolean
		 */
		public boolean submitVote(Poll.QuestionOption pollQuestionOption) {
			return this.poll.submitVote(this, pollQuestionOption);
		}
		
		// }}}
	}
	
	/**
	 * Represents a question option.
	 * @author Wong Yong Jie
	 */
	public class QuestionOption extends IVLEObject {
		// {{{ properties
		
		/** ID */
		public final String ID;
		
		/** Poll */
		public final Poll poll;
		
		/** Question ID */
		public final Poll.Question pollQuestion;
		
		/** Option text */
		public final String optionText;
		
		/** Order */
		public final Integer order;
		
		/** Voted count */
		public final Integer votedCount;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		QuestionOption() {
			throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
		}
		
		QuestionOption(IVLE ivle, Map<?, ?> map, Poll poll, Poll.Question pollQuestion) {
			// Set our IVLE object.
			this.ivle = ivle;
			
			// Read data from JSON.
			this.poll = poll;
			this.pollQuestion = pollQuestion;
			this.ID = extractString("ID", map);
			this.optionText = extractString("OptionText", map);
			this.order = extractInt("Order", map);
			this.votedCount = extractInt("VotedCount", map);
		}
		
		/**
		 * Method: getVotedUser
		 * Get details (ID, name and email) of the users who voted this option.
		 * 
		 * @return User[]
		 */
		public User[] getVotedUser() throws Exception {
			return this.poll.getVotedUser(this.pollQuestion, this);
		}
		
		/**
		 * Method: submitVote
		 * Votes for a particular option on the poll.
		 * XXX: Not implemented yet.
		 * 
		 * @return boolean
		 */
		public boolean submitVote() {
			return this.poll.submitVote(this.pollQuestion, this);
		}
		
		// }}}
	}
	
	// }}}
}
