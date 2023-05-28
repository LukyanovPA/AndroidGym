package repository

interface AnswerFeedbackRepository : suspend (Int, String) -> Unit