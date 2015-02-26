package com.jparkie.givesmehope.data.interpreters;

import com.jparkie.givesmehope.data.factories.DefaultFactory;
import com.jparkie.givesmehope.models.Anthology;
import com.jparkie.givesmehope.models.Story;
import com.jparkie.givesmehope.utils.CategoryUtils;

import java.util.ArrayList;
import java.util.List;

public class MockGMHInterpreterImpl implements GMHInterpreter {
    public static final String TAG = MockGMHInterpreterImpl.class.getSimpleName();

    public MockGMHInterpreterImpl() {}

    @Override
    public Anthology interpretHotAnthologyFromString(String uninterpretedString) {
        final Anthology hotAnthology = DefaultFactory.Anthology.constructDefaultInstance();

        List<Story> hotStories = new ArrayList<>();

        Story firstStory = DefaultFactory.Story.constructDefaultInstance();
        firstStory.setUrl("http://mobile.givesmehope.com/Inspiring+Feats/A-boy-was-dying-of-cancer-and-needed-an/2300?m");
        firstStory.setPostId("2300");
        firstStory.setFooter("Jun 18, 2009 at 7:00pm by lennyALSF");
        firstStory.setTitle("A boy was dying of cancer and needed an expensive brain surgery, but his family, broke and desperate, couldn't afford it.");
        firstStory.setCategory(CategoryUtils.CATEGORY_INSPIRING_FEATS);
        firstStory.setStory("His 8 year old sister Tess took her piggy bank savings to a pharmacist in order to buy a 'miracle'. It just so happens that the right man witnessed the little girl's tears at the pharmacy counter: a neurosurgeon. He performed the surgery for free.");
        firstStory.setImageUrl("file:///android_asset/mock/images/Hot_1.jpg");
        hotStories.add(firstStory);

        Story secondStory = DefaultFactory.Story.constructDefaultInstance();
        secondStory.setUrl("http://mobile.givesmehope.com/Random+Acts+Of+Kindness/Today-my-school-had-a-fire-drill/59082?m");
        secondStory.setPostId("59082");
        secondStory.setFooter("May 16, 2010 at 12:00pm by Rachel, Griffith IN");
        secondStory.setTitle("Today my school had a fire drill.");
        secondStory.setCategory(CategoryUtils.CATEGORY_RANDOM_ACTS_OF_KINDNESS);
        secondStory.setStory("I was standing outside with one of the most popular football players, when a Down Syndrome girl came up to him and wanted to hold his hand because she was scared.  Happily, he held her hand in front of all his friends back to class. His soft side in front of his boys GMH");
        secondStory.setImageUrl("file:///android_asset/mock/images/Hot_2.jpg");
        hotStories.add(secondStory);

        Story thirdStory = DefaultFactory.Story.constructDefaultInstance();
        thirdStory.setUrl("http://mobile.givesmehope.com/Amazing+Friends/A-little-girl-was-dying-of-cancer-and-he/19551?m");
        thirdStory.setPostId("19551");
        thirdStory.setFooter("May 20, 2010 at 1:00am by Anonymous");
        thirdStory.setTitle("A little girl was dying of cancer and her younger brother had a match for the bone marrow she needed.");
        thirdStory.setCategory(CategoryUtils.CATEGORY_AMAZING_FRIENDS);
        thirdStory.setStory("The doctors told him it was a matter of life and death. After he had the surgery, he asked the doctors how long he had to live. He thought if he gave his bone marrow to let his sister live he would die - but he did it anyway.");
        thirdStory.setImageUrl("file:///android_asset/mock/images/Hot_3.jpg");
        hotStories.add(thirdStory);

        Story fourthStory = DefaultFactory.Story.constructDefaultInstance();
        fourthStory.setUrl("http://mobile.givesmehope.com/Amazing+Friends/My-little-sister-came-home-from-school-o/43120?m");
        fourthStory.setPostId("43120");
        fourthStory.setFooter("May 20, 2010 at 1:00am by Elena D, Dallastown, PA");
        fourthStory.setTitle("My little sister came home from school one day and demanded I take her to the library so she could get books on sign language.");
        fourthStory.setCategory(CategoryUtils.CATEGORY_AMAZING_FRIENDS);
        fourthStory.setStory("I asked why? She told me there was a new kid at school who was deaf and she wanted to befriend him. Today, I stood beside her at their wedding watching her sign... \"I DO\".");
        fourthStory.setImageUrl("file:///android_asset/mock/images/Hot_4.jpg");
        hotStories.add(fourthStory);

        Story fifthStory = DefaultFactory.Story.constructDefaultInstance();
        fifthStory.setUrl("http://mobile.givesmehope.com/Random+Acts+Of+Kindness/In-8th-grade-there-was-a-girl-with-speci/59555?m");
        fifthStory.setPostId("59555");
        fifthStory.setFooter("May 19, 2010 at 2:00pm by alysia, NJ");
        fifthStory.setTitle("In 8th grade, there was a girl with special needs in my class.");
        fifthStory.setCategory(CategoryUtils.CATEGORY_RANDOM_ACTS_OF_KINDNESS);
        firstStory.setStory("One day while walking home, I saw some mean boys telling her that there was gold in a puddle of mud. She ran over to the puddle of mud and started splashing in it, and the boys laughed as she got dirty. Instead of laughing, another boy in my class went up and started playing in the mud with her. He GMH");
        fifthStory.setImageUrl("file:///android_asset/mock/images/Hot_5.jpg");
        hotStories.add(fifthStory);

        hotAnthology.setStories(hotStories);
        hotAnthology.setNextPageUrl("http://mobile.givesmehope.com/bestof/month/2?btn");

        return hotAnthology;
    }

    @Override
    public Anthology interpretTrendingAnthologyFromString(String uninterpretedString) {
        final Anthology trendingAnthology = DefaultFactory.Anthology.constructDefaultInstance();

        List<Story> trendingStories = new ArrayList<>();

        Story firstStory = DefaultFactory.Story.constructDefaultInstance();
        firstStory.setUrl("http://mobile.givesmehope.com/Inspiring+Feats/A-boy-was-dying-of-cancer-and-needed-an/2300?m");
        firstStory.setPostId("2300");
        firstStory.setFooter("Jun 18, 2009 at 7:00pm by lennyALSF");
        firstStory.setTitle("A boy was dying of cancer and needed an expensive brain surgery, but his family, broke and desperate, couldn't afford it.");
        firstStory.setCategory(CategoryUtils.CATEGORY_INSPIRING_FEATS);
        firstStory.setStory("His 8 year old sister Tess took her piggy bank savings to a pharmacist in order to buy a 'miracle'. It just so happens that the right man witnessed the little girl's tears at the pharmacy counter: a neurosurgeon. He performed the surgery for free.");
        firstStory.setImageUrl("file:///android_asset/mock/images/Hot_1.jpg");
        trendingStories.add(firstStory);

        Story secondStory = DefaultFactory.Story.constructDefaultInstance();
        secondStory.setUrl("http://mobile.givesmehope.com/Random+Acts+Of+Kindness/Today-my-school-had-a-fire-drill/59082?m");
        secondStory.setPostId("59082");
        secondStory.setFooter("May 16, 2010 at 12:00pm by Rachel, Griffith IN");
        secondStory.setTitle("Today my school had a fire drill.");
        secondStory.setCategory(CategoryUtils.CATEGORY_RANDOM_ACTS_OF_KINDNESS);
        secondStory.setStory("I was standing outside with one of the most popular football players, when a Down Syndrome girl came up to him and wanted to hold his hand because she was scared.  Happily, he held her hand in front of all his friends back to class. His soft side in front of his boys GMH");
        secondStory.setImageUrl("file:///android_asset/mock/images/Hot_2.jpg");
        trendingStories.add(secondStory);

        Story thirdStory = DefaultFactory.Story.constructDefaultInstance();
        thirdStory.setUrl("http://mobile.givesmehope.com/Amazing+Friends/A-little-girl-was-dying-of-cancer-and-he/19551?m");
        thirdStory.setPostId("19551");
        thirdStory.setFooter("May 20, 2010 at 1:00am by Anonymous");
        thirdStory.setTitle("A little girl was dying of cancer and her younger brother had a match for the bone marrow she needed.");
        thirdStory.setCategory(CategoryUtils.CATEGORY_AMAZING_FRIENDS);
        thirdStory.setStory("The doctors told him it was a matter of life and death. After he had the surgery, he asked the doctors how long he had to live. He thought if he gave his bone marrow to let his sister live he would die - but he did it anyway.");
        thirdStory.setImageUrl("file:///android_asset/mock/images/Hot_3.jpg");
        trendingStories.add(thirdStory);

        Story fourthStory = DefaultFactory.Story.constructDefaultInstance();
        fourthStory.setUrl("http://mobile.givesmehope.com/Amazing+Friends/My-little-sister-came-home-from-school-o/43120?m");
        fourthStory.setPostId("43120");
        fourthStory.setFooter("May 20, 2010 at 1:00am by Elena D, Dallastown, PA");
        fourthStory.setTitle("My little sister came home from school one day and demanded I take her to the library so she could get books on sign language.");
        fourthStory.setCategory(CategoryUtils.CATEGORY_AMAZING_FRIENDS);
        fourthStory.setStory("I asked why? She told me there was a new kid at school who was deaf and she wanted to befriend him. Today, I stood beside her at their wedding watching her sign... \"I DO\".");
        fourthStory.setImageUrl("file:///android_asset/mock/images/Hot_4.jpg");
        trendingStories.add(fourthStory);

        Story fifthStory = DefaultFactory.Story.constructDefaultInstance();
        fifthStory.setUrl("http://mobile.givesmehope.com/Random+Acts+Of+Kindness/In-8th-grade-there-was-a-girl-with-speci/59555?m");
        fifthStory.setPostId("59555");
        fifthStory.setFooter("May 19, 2010 at 2:00pm by alysia, NJ");
        fifthStory.setTitle("In 8th grade, there was a girl with special needs in my class.");
        fifthStory.setCategory(CategoryUtils.CATEGORY_RANDOM_ACTS_OF_KINDNESS);
        firstStory.setStory("One day while walking home, I saw some mean boys telling her that there was gold in a puddle of mud. She ran over to the puddle of mud and started splashing in it, and the boys laughed as she got dirty. Instead of laughing, another boy in my class went up and started playing in the mud with her. He GMH");
        fifthStory.setImageUrl("file:///android_asset/mock/images/Hot_5.jpg");
        trendingStories.add(fifthStory);

        trendingAnthology.setStories(trendingStories);
        trendingAnthology.setNextPageUrl("http://mobile.givesmehope.com/bestof/month/2?btn");

        return trendingAnthology;
    }

    @Override
    public Story interpretVoteStoryFromString(String uninterpretedString) {
        Story voteStory = DefaultFactory.Story.constructDefaultInstance();
        voteStory.setUrl("http://mobile.givesmehope.com/Inspiring+Feats/A-boy-was-dying-of-cancer-and-needed-an/2300?m");
        voteStory.setPostId("2300");
        voteStory.setFooter("Jun 18, 2009 at 7:00pm by lennyALSF");
        voteStory.setTitle("A boy was dying of cancer and needed an expensive brain surgery, but his family, broke and desperate, couldn't afford it.");
        voteStory.setCategory(CategoryUtils.CATEGORY_INSPIRING_FEATS);
        voteStory.setStory("His 8 year old sister Tess took her piggy bank savings to a pharmacist in order to buy a 'miracle'. It just so happens that the right man witnessed the little girl's tears at the pharmacy counter: a neurosurgeon. He performed the surgery for free.");
        voteStory.setImageUrl("file:///android_asset/mock/images/Hot_1.jpg");

        return voteStory;
    }
}
