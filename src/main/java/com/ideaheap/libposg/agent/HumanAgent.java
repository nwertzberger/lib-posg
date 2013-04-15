package com.ideaheap.libposg.agent;

/**
 * User: nwertzberger
 * Date: 4/14/13
 * Time: 9:25 PM
 * Email: wertnick@gmail.com
 * <p/>
 * It looks like I forgot to explain this class.
 */
public class HumanAgent implements Agent {
    private String name;

    public HumanAgent(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
