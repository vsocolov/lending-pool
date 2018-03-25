package com.vsocolov.lendingpool.datasource.services;


import com.vsocolov.lendingpool.commons.data.LenderRecord;

import java.nio.file.Path;
import java.util.List;

public interface InputSourceReader {
    List<LenderRecord> parseLendersSource(Path path);
}
