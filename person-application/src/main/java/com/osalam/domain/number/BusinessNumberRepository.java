package com.osalam.domain.number;

import com.osalam.domain.share.AddressNumber;
import com.osalam.domain.vo.PNumber;

public interface BusinessNumberRepository {
    PNumber fetchNextPNumber();

    AddressNumber fetchNextAddressNumber();
}
