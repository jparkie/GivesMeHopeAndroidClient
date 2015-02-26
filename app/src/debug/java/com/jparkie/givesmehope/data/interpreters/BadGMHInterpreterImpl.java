package com.jparkie.givesmehope.data.interpreters;

import com.jparkie.givesmehope.models.Anthology;
import com.jparkie.givesmehope.models.Story;

public class BadGMHInterpreterImpl implements GMHInterpreter {
    public static final String TAG = BadGMHInterpreterImpl.class.getSimpleName();

    public BadGMHInterpreterImpl() {}

    @Override
    public Anthology interpretHotAnthologyFromString(String uninterpretedString) {
        throw new NullPointerException(TAG + ": interpretHotAnthologyFromString() - Simulated Bad Parsing.");
    }

    @Override
    public Anthology interpretTrendingAnthologyFromString(String uninterpretedString) {
        throw new NullPointerException(TAG + ": interpretTrendingAnthologyFromString() - Simulated Bad Parsing.");
    }

    @Override
    public Story interpretVoteStoryFromString(String uninterpretedString) {
        throw new NullPointerException(TAG + ": interpretVoteStoryFromString() - Simulated Bad Parsing.");
    }
}
