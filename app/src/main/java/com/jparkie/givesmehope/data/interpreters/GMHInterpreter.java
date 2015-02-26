package com.jparkie.givesmehope.data.interpreters;

import com.jparkie.givesmehope.models.Anthology;
import com.jparkie.givesmehope.models.Story;

public interface GMHInterpreter {
    public Anthology interpretHotAnthologyFromString(String uninterpretedString);

    public Anthology interpretTrendingAnthologyFromString(String uninterpretedString);

    public Story interpretVoteStoryFromString(String uninterpretedString);
}
