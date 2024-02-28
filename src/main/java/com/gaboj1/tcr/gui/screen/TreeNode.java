package com.gaboj1.tcr.gui.screen;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

//为什么就没有现成的库呢因为太简单了吗
public class TreeNode {

    private Component answer = Component.empty();
    private Component option;
    @Nullable
    private List<TreeNode> options;

    public TreeNode(Component option) {
        this.option = option;
        this.options = new ArrayList<>();
    }

    public TreeNode(Component answer, Component option) {
        this.answer = answer;
        this.option = option;
        this.options = new ArrayList<>();
    }

    public void addOption(Component answer, Component option) {
        options.add(new TreeNode(answer, option));
    }
    public void addChild(TreeNode node) {
        options.add(node);
    }

    public Component getAnswer() {
        return answer;
    }

    public Component getOption() {
        return option;
    }

    @Nullable
    public List<TreeNode> getChildren(){
        return options;
    }

    public static class FinalNode extends TreeNode{

        private byte returnValue;
        public FinalNode(Component finalOption, byte returnValue) {
            super(finalOption);
            this.returnValue = returnValue;
        }

        public byte getReturnValue(){
            return returnValue;
        }

    }

}

