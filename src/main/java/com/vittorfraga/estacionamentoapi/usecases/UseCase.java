package com.vittorfraga.estacionamentoapi.usecases;

public abstract class UseCase<Input, Output> {

    /**
     * Executes the use case with the provided input.
     *
     * @param anInput The input for the use case.
     * @return The result of the use case execution.
     */
    public abstract Output execute(Input anInput);

}
