package com.vittorfraga.estacionamentoapi.domain.establishment;

import com.vittorfraga.estacionamentoapi.domain.Validator;
import com.vittorfraga.estacionamentoapi.domain.exceptions.establishment.InvalidCnpjLengthException;
import com.vittorfraga.estacionamentoapi.domain.exceptions.establishment.NegativeSlotException;


public class EstablishmentValidator {
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 255;
    private static final int PHONE_MAX_LENGTH = 30;
    private static final int CNPJ_LENGTH = 14;

    public static void validateEstablishmentFields(Establishment establishment) {
        Validator.validateNotNullOrBlank(establishment.getName(), "name");
        Validator.validateNotNullOrBlank(establishment.getCnpj(), "cnpj");
        Validator.validateNotNullOrBlank(establishment.getAddress(), "address");
        Validator.validateNotNullOrBlank(establishment.getPhone(), "phone");
        Validator.validateNotNull(establishment.getMotorcycleSlots(), "motorcycleSlots");
        Validator.validateNotNull(establishment.getCarSlots(), "carSlots");

        Validator.validateLength(establishment.getName(), "name", NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        Validator.validateLength(establishment.getPhone(), "phone", 1, PHONE_MAX_LENGTH);

        validateSlotNotNegative(establishment.getMotorcycleSlots(), "motorcycleSlots");
        validateSlotNotNegative(establishment.getCarSlots(), "carSlots");

        validateCnpjLength(establishment);
    }

    private static void validateSlotNotNegative(Integer slot, String slotName) {
        Validator.validateNotNull(slot, slotName);
        if (slot < 0) {
            throw new NegativeSlotException(slotName);
        }
    }

    private static void validateCnpjLength(Establishment establishment) {
        int expectedLength = CNPJ_LENGTH;
        if (establishment.getCnpj().length() != expectedLength) {
            throw new InvalidCnpjLengthException(expectedLength);
        }
    }

}