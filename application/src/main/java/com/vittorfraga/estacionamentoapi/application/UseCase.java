package com.vittorfraga.estacionamentoapi.application;

public interface UseCase<Input, Output> {

    /**
     * Executes the use case with the provided input.
     *
     * @param anInput The input for the use case.
     * @return The result of the use case execution.
     */
    Output execute(Input anInput);

}
