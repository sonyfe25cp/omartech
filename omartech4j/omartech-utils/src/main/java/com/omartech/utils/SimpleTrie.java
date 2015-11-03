package com.omartech.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单版本的trie树
 *
 * @author Chen Jie
 * @date 13 Sep, 2014
 */
public class SimpleTrie {

    static Logger logger = LoggerFactory.getLogger(SimpleTrie.class);
    Node root;

    public SimpleTrie() {
        root = new Node();
    }

    public void add(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        Node node = root;
        for (int i = 0; i < str.length(); i++) {
            //String curr = str.charAt(i) + "";
            char curr = str.charAt(i);
            boolean containsKey = node.son.containsKey(curr);
            if (containsKey) {

            } else {
                Node newNode = new Node();
                newNode.value = curr;
                node.son.put(curr, newNode);
            }
            node = node.son.get(curr);
        }
        node.isEnd = true;
    }

    public boolean contains(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            //String curr = word.charAt(i) + "";
            char curr = word.charAt(i);
            boolean containsKey = node.son.containsKey(curr);
            if (containsKey) {
                node = node.son.get(curr);
            } else {
                return false;
            }
        }
        boolean isEnd = node.isEnd;
        return isEnd;
    }

    /**
     * @param sentence
     * @return
     */
    public List<String> matchedFromTrie(String sentence) {
        List<String> set = new ArrayList<>();
        if (sentence == null || sentence.length() == 0) {
            return set;
        }
        for (int i = 0; i < sentence.length(); i++) {
            Node node = root;
            StringBuilder sb = null;
            for (int j = i; j < sentence.length(); j++) {
                //String curr = sentence.charAt(j) + "";
                char curr = sentence.charAt(j);
                boolean containsKey = node.son.containsKey(curr);
                if (containsKey) {
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    node = node.son.get(curr);
                    sb.append(node.value);
                    if (node.isEnd) {
                        if (sb != null) {
                            set.add(sb.toString());
                        }
                    }
                } else {
                    boolean isEnd = node.isEnd;
                    if (isEnd) {
                        if (sb != null) {
                            i = j - 1;
                        } else {
                        }
                    } else {
                        break;
                    }
                    sb = null;
                }
            }
        }
        return set;
    }


    public static void main(String[] args) {
        SimpleTrie st = new SimpleTrie();
        st.add("beijing");
        st.add("beifang");
        st.add("haha");
        st.add("hahajing");
        String sentence = "hahajing beijingfang beifang anghaha";
        List<String> matchedFromTrie = st.matchedFromTrie(sentence);
        for (String s : matchedFromTrie) {
            logger.info("array: {}", s);
        }
    }

    class Node {

        Map<Character, Node> son;

        boolean isEnd;

        char value;

        public Node() {
            super();
            son = new HashMap<>();
        }
    }
}
