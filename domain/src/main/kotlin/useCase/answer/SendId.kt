package useCase.answer

import entity.answer.IdStorage

interface SendId : suspend (Int) -> Unit

internal class SendIdImpl(
    private val idStorage: IdStorage
) : SendId {
    override suspend operator fun invoke(id: Int) {
        idStorage.set(value = id)
    }
}