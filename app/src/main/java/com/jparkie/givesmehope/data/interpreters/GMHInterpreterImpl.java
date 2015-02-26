package com.jparkie.givesmehope.data.interpreters;

import com.jparkie.givesmehope.data.factories.DefaultFactory;
import com.jparkie.givesmehope.models.Anthology;
import com.jparkie.givesmehope.models.Story;
import com.jparkie.givesmehope.utils.CategoryUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public final class GMHInterpreterImpl implements GMHInterpreter {
    public static final String TAG = GMHInterpreterImpl.class.getSimpleName();

    public GMHInterpreterImpl() {}

    @Override
    public Anthology interpretHotAnthologyFromString(String uninterpretedString) {
        final String CONSTANT_MAIN_DATA_ELEMENT = "page_wrapper";
        final String CONSTANT_STORY_ELEMENTS = "div.post[id^=post]";
        final String CONSTANT_STORY_BODY_ELEMENT = "div.body";
        final String CONSTANT_STORY_FOOTER_ELEMENT = "div.footer";
        final String CONSTANT_NEXT_PAGE_URL_ELEMENT = "next_link";

        final Anthology hotAnthology = DefaultFactory.Anthology.constructDefaultInstance();
        List<Story> hotStories = null;
        String hotNextPageUrl = null;

        Document parsedDocument = Jsoup.parse(uninterpretedString);
        Element mainDataElement = parsedDocument.getElementById(CONSTANT_MAIN_DATA_ELEMENT);
        Elements storyElements = mainDataElement.select(CONSTANT_STORY_ELEMENTS);

        hotStories = new ArrayList<>();
        for (int index = 0; index < storyElements.size(); index++) {
            Element currentStoryElement = storyElements.get(index);
            Element currentStoryBodyElement = currentStoryElement.select(CONSTANT_STORY_BODY_ELEMENT).first();
            Element currentStoryFooterElement = currentStoryElement.select(CONSTANT_STORY_FOOTER_ELEMENT).first();

            Story currentStory = DefaultFactory.Story.constructDefaultInstance();
            String currentStoryUrl = null;
            String currentPostId = null;
            String currentStoryTitle = null;
            String currentStoryCategory = null;
            String currentStoryFooter = null;
            String currentStoryImageUrl = null;

            currentStoryUrl = currentStoryFooterElement.select("div.fb-like").first().attr("data-href");
            currentPostId = currentStoryUrl.substring(currentStoryUrl.lastIndexOf("/") + 1).replace("?m", "");

            if (currentStoryBodyElement.select("strong").first() != null) {
                currentStoryTitle = currentStoryBodyElement.select("strong").first().text();
            }

            if (currentStoryUrl.contains("/Amazing+Friends/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_AMAZING_FRIENDS;
            } else if (currentStoryUrl.contains("/Cute+Kids/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_CUTE_KIDS;
            } else if (currentStoryUrl.contains("/Inspiring+Feats/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_INSPIRING_FEATS;
            } else if (currentStoryUrl.contains("/Other/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_OTHER;
            } else if (currentStoryUrl.contains("/Potter/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_POTTER;
            } else if (currentStoryUrl.contains("/Random+Acts+Of+Kindness/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_RANDOM_ACTS_OF_KINDNESS;
            }

            currentStoryFooter = currentStoryFooterElement.text().replace("Tweet", "").trim();

            currentStoryImageUrl = currentStoryBodyElement.select("img").first().attr("src");

            if (currentStoryUrl != null) {
                currentStory.setUrl(currentStoryUrl);
            }
            if (currentPostId != null) {
                currentStory.setPostId(currentPostId);
            }
            if (currentStoryFooter != null) {
                currentStory.setFooter(currentStoryFooter);
            }
            if (currentStoryTitle != null) {
                currentStory.setTitle(currentStoryTitle);
            }
            if (currentStoryCategory != null) {
                currentStory.setCategory(currentStoryCategory);
            }
            if (currentStoryImageUrl != null) {
                currentStory.setImageUrl(currentStoryImageUrl);
            }

            hotStories.add(currentStory);
        }

        Element nextPageUrlElement = parsedDocument.getElementById(CONSTANT_NEXT_PAGE_URL_ELEMENT);
        if (nextPageUrlElement != null) {
            hotNextPageUrl = "http://mobile.givesmehope.com" + nextPageUrlElement.attr("href").replace("//", "/");
        }

        hotAnthology.setStories(hotStories);
        hotAnthology.setNextPageUrl(hotNextPageUrl);

        return hotAnthology;
    }

    @Override
    public Anthology interpretTrendingAnthologyFromString(String uninterpretedString) {
        final String CONSTANT_MAIN_DATA_ELEMENT = "page_wrapper";
        final String CONSTANT_STORY_ELEMENTS = "div.post[id^=post]";
        final String CONSTANT_STORY_BODY_ELEMENT = "div.body";
        final String CONSTANT_STORY_FOOTER_ELEMENT = "div.footer";
        final String CONSTANT_NEXT_PAGE_URL_ELEMENT = "next_link";

        final Anthology trendingAnthology = DefaultFactory.Anthology.constructDefaultInstance();
        List<Story> trendingStories = null;
        String trendingNextPageUrl = null;

        Document parsedDocument = Jsoup.parse(uninterpretedString);
        Element mainDataElement = parsedDocument.getElementById(CONSTANT_MAIN_DATA_ELEMENT);
        Elements storyElements = mainDataElement.select(CONSTANT_STORY_ELEMENTS);

        trendingStories = new ArrayList<>();
        for (int index = 0; index < storyElements.size(); index++) {
            Element currentStoryElement = storyElements.get(index);
            Element currentStoryBodyElement = currentStoryElement.select(CONSTANT_STORY_BODY_ELEMENT).first();
            Element currentStoryFooterElement = currentStoryElement.select(CONSTANT_STORY_FOOTER_ELEMENT).first();

            Story currentStory = DefaultFactory.Story.constructDefaultInstance();
            String currentStoryUrl = null;
            String currentPostId = null;
            String currentStoryTitle = null;
            String currentStoryCategory = null;
            String currentStoryStory = null;
            String currentStoryFooter = null;

            currentStoryUrl = currentStoryFooterElement.select("div.fb-like").first().attr("data-href");
            currentPostId = currentStoryUrl.substring(currentStoryUrl.lastIndexOf("/") + 1).replace("?m", "");

            if (currentStoryBodyElement.select("strong").first() != null) {
                currentStoryTitle = currentStoryBodyElement.select("strong").first().text();
            }

            if (currentStoryUrl.contains("/Amazing+Friends/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_AMAZING_FRIENDS;
            } else if (currentStoryUrl.contains("/Cute+Kids/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_CUTE_KIDS;
            } else if (currentStoryUrl.contains("/Inspiring+Feats/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_INSPIRING_FEATS;
            } else if (currentStoryUrl.contains("/Other/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_OTHER;
            } else if (currentStoryUrl.contains("/Potter/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_POTTER;
            } else if (currentStoryUrl.contains("/Random+Acts+Of+Kindness/")) {
                currentStoryCategory = CategoryUtils.CATEGORY_RANDOM_ACTS_OF_KINDNESS;
            }

            if (currentStoryTitle != null) {
                currentStoryStory = currentStoryBodyElement.text().replace(currentStoryTitle, "").trim();
            } else {
                currentStoryStory = currentStoryBodyElement.text().trim();
            }

            currentStoryFooter = currentStoryFooterElement.text().replace("Tweet", "").trim();

            if (currentStoryUrl != null) {
                currentStory.setUrl(currentStoryUrl);
            }
            if (currentPostId != null) {
                currentStory.setPostId(currentPostId);
            }
            if (currentStoryFooter != null) {
                currentStory.setFooter(currentStoryFooter);
            }
            if (currentStoryTitle != null) {
                currentStory.setTitle(currentStoryTitle);
            }
            if (currentStoryCategory != null) {
                currentStory.setCategory(currentStoryCategory);
            }
            if (currentStoryStory != null) {
                currentStory.setStory(currentStoryStory);
            }

            trendingStories.add(currentStory);
        }

        Element nextPageUrlElement = parsedDocument.getElementById(CONSTANT_NEXT_PAGE_URL_ELEMENT);
        if (nextPageUrlElement != null) {
            trendingNextPageUrl = "http://mobile.givesmehope.com" + nextPageUrlElement.attr("href").replace("//", "/");
        }

        trendingAnthology.setStories(trendingStories);
        trendingAnthology.setNextPageUrl(trendingNextPageUrl);

        return trendingAnthology;
    }

    @Override
    public Story interpretVoteStoryFromString(String uninterpretedString) {
        final String CONSTANT_STORY_ELEMENTS = "div.post[id^=post]";
        final String CONSTANT_STORY_BODY_ELEMENT = "div.body";
        final String CONSTANT_STORY_FOOTER_ELEMENT = "div.footer";

        final Story voteStory = DefaultFactory.Story.constructDefaultInstance();
        String storyUrl = null;
        String storyPostId = null;
        String storyCategory = null;
        String storyFooter = null;
        String storyImageUrl = null;

        Document parsedDocument = Jsoup.parse(uninterpretedString);
        Element storyElement = parsedDocument.select(CONSTANT_STORY_ELEMENTS).first();
        Element storyBodyElement = storyElement.select(CONSTANT_STORY_BODY_ELEMENT).first();
        Element storyFooterElement = storyElement.select(CONSTANT_STORY_FOOTER_ELEMENT).first();

        storyUrl = storyBodyElement.select("a").first().attr("href");
        storyPostId = storyUrl.substring(storyUrl.lastIndexOf("/") + 1).replace("?m", "");

        if (storyUrl.contains("/Amazing+Friends/")) {
            storyCategory = CategoryUtils.CATEGORY_AMAZING_FRIENDS;
        } else if (storyUrl.contains("/Cute+Kids/")) {
            storyCategory = CategoryUtils.CATEGORY_CUTE_KIDS;
        } else if (storyUrl.contains("/Inspiring+Feats/")) {
            storyCategory = CategoryUtils.CATEGORY_INSPIRING_FEATS;
        } else if (storyUrl.contains("/Other/")) {
            storyCategory = CategoryUtils.CATEGORY_OTHER;
        } else if (storyUrl.contains("/Potter/")) {
            storyCategory = CategoryUtils.CATEGORY_POTTER;
        } else if (storyUrl.contains("/Random+Acts+Of+Kindness/")) {
            storyCategory = CategoryUtils.CATEGORY_RANDOM_ACTS_OF_KINDNESS;
        }

        storyFooter = storyFooterElement.text().replace("Tweet", "").trim();

        storyImageUrl = storyBodyElement.select("img").first().attr("src");

        voteStory.setUrl(storyUrl);
        voteStory.setPostId(storyPostId);
        voteStory.setCategory(storyCategory);
        voteStory.setFooter(storyFooter);
        voteStory.setImageUrl(storyImageUrl);

        return voteStory;
    }
}
