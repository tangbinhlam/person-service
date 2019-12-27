package com.osalam.data.rest;

import com.osalam.domain.number.BusinessNumberRepository;
import com.osalam.domain.share.AddressNumber;
import com.osalam.domain.vo.PNumber;

import java.util.Random;

public class BusinessNumberFakeAdapater implements BusinessNumberRepository {
    private final Random r = new Random();

    @Override
    public PNumber fetchNextPNumber() {
        int highest = 99999999;
        int lowest = 10000000;
        int generatedId = r.nextInt(highest - lowest) + lowest;
        return PNumber.of(generatedId);
    }

    @Override
    public AddressNumber fetchNextAddressNumber() {
        int highest = 999999999;
        int lowest = 100000000;
        int generatedId = r.nextInt(highest - lowest) + lowest;
        return AddressNumber.of(generatedId);
    }
}
