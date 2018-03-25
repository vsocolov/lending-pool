package com.vsocolov.lendingpool.processor.converter;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.data.LendingBundle;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.convert.converter.Converter;

import java.util.stream.IntStream;

public class LenderRecordToLenderConverter implements Converter<LenderRecord, Lender> {

    private Integer bundleSplitAmount;

    @Required
    public void setBundleSplitAmount(Integer bundleSplitAmount) {
        this.bundleSplitAmount = bundleSplitAmount;
    }

    @Override
    public Lender convert(final LenderRecord source) {
        final Lender destination = new Lender(source.getName(), source.getRate(), source.getAmount());
        addBundles(destination);

        return destination;
    }

    private void addBundles(final Lender lender) {
        IntStream.rangeClosed(1, (int) lender.getAmount() / bundleSplitAmount)
                .forEach(nr -> lender.addBundle(new LendingBundle(lender, bundleSplitAmount)));
    }
}
