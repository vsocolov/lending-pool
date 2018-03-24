package com.vsocolov.lendingpool.datasource.services;

import com.vsocolov.lendingpool.datasource.data.Lender;

import java.nio.file.Path;
import java.util.List;

public interface InputSourceReader {
    List<Lender> parseLendersSource(Path path);
}
