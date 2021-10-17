package com.dnapayments.data.repository

import com.dnapayments.R
import com.dnapayments.api_clients.IMainService
import com.dnapayments.data.Resource
import com.dnapayments.data.model.*
import com.dnapayments.utils.simpleError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val service: IMainService) {
    suspend fun fetchCharacters():
            Resource<List<Character>> {
        return try {
            withContext(Dispatchers.IO) {
                val result = service.getCharacterListAsync(
                ).await()
                Resource.success(result)
            }
        } catch (e: Exception) {
            Resource.error(R.string.something_went_wrong)
        }
    }

    suspend fun fetchCourseList():
            SimpleResult<CoursesResponse> {
        return try {
            withContext(Dispatchers.IO) {
                service.getCoursesAsync().await().getSimpleResult()
            }
        } catch (e: Exception) {
            e.simpleError()
        }
    }

    suspend fun fetchQuizzes(lessonId: Int):
            SimpleResult<QuizResponse> {
        return try {
            withContext(Dispatchers.IO) {
                service.getQuizzesAsync(lessonId).await().getSimpleResult()
            }
        } catch (e: Exception) {
            e.simpleError()
        }
    }

    suspend fun fetchLesson(courseId: Int):
            SimpleResult<LessonResponse> {
        return try {
            withContext(Dispatchers.IO) {
                service.getLessonsAsync(courseId).await().getSimpleResult()
            }
        } catch (e: Exception) {
            e.simpleError()
        }
    }

    suspend fun saveQuizResult(lessonId: Int, passed: Int, userId: Int, correct: Int):
            SimpleResult<Sample> {
        return try {
            withContext(Dispatchers.IO) {
                val data = service.saveQuizResultAsync(hashMapOf(
                    "lesson_id" to lessonId,
                    "passed" to passed,
                    "user_id" to userId,
                    "correct" to correct
                )).await()
                if (data.status!!) {
                    SimpleResult.Success(data)
                } else {
                    SimpleResult.Error(data.message!!)
                }
            }
        } catch (e: Exception) {
            e.simpleError()
        }
    }
}
