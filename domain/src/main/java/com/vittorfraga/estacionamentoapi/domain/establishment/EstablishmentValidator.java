package com.vittorfraga.estacionamentoapi.domain.establishment;

import com.vittorfraga.estacionamentoapi.domain.validation.Error;
import com.vittorfraga.estacionamentoapi.domain.validation.ValidationHandler;
import com.vittorfraga.estacionamentoapi.domain.validation.Validator;


public class EstablishmentValidator extends Validator {
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 255;
    private static final int PHONE_MAX_LENGTH = 30;
    private static final int CNPJ_LENGTH = 14;

    private final Establishment establishment;

    protected EstablishmentValidator(final Establishment anEstablishment, final ValidationHandler aHandler) {
        super(aHandler);
        this.establishment = anEstablishment;
    }

    @Override
    public void validate() {
        validateNotNullOrBlank(this.establishment.getName(), "name");
        validateNotNullOrBlank(this.establishment.getCnpj(), "cnpj");
        validateNotNullOrBlank(this.establishment.getAddress(), "address");
        validateNotNullOrBlank(this.establishment.getPhone(), "phone");
        validateNotNull(this.establishment.getMotorcycleSlots(), "motorcycleSlots");
        validateNotNull(this.establishment.getCarSlots(), "carSlots");

        validateLength(this.establishment.getName(), "name", NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        validateLength(this.establishment.getPhone(), "phone", 1, PHONE_MAX_LENGTH);

        validateSlotNotNegative(this.establishment.getMotorcycleSlots(), "motorcycleSlots");
        validateSlotNotNegative(this.establishment.getCarSlots(), "carSlots");

        validateCnpjLength();
    }


    private void validateSlotNotNegative(Integer slot, String slotName) {
        validateNotNull(slot, slotName);
        if (slot < 0) {
            this.validationHandler().append(new Error((slotName + " should not be negative")));
        }
    }

    private void validateCnpjLength() {
        if (this.establishment.getCnpj().length() != CNPJ_LENGTH) {
            this.validationHandler().append(new Error("cnpj must be " + CNPJ_LENGTH + " characters"));
        }
    }


}