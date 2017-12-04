import java.util.EmptyStackException;
import java.util.Queue;
import java.util.Stack;

/*
 * SD2x Homework #2
 * Implement the method below according to the specification in the assignment description.
 * Please be sure not to change the method signature!
 */

public class HtmlValidator {

    //If the input has no tags, return null
    public static Stack<HtmlTag> isValidHtml(Queue<HtmlTag> tags) throws java.util.EmptyStackException {
    if (tags.isEmpty())
                return null;

   Stack<HtmlTag> htmlTagStack = new Stack<>();
    while (!tags.isEmpty()) {
        //if the tag is self closing, then remove it from the queue
        if (tags.peek().isSelfClosing()) {
            tags.poll();
            continue;
        }

        //If the tag is an opening tag, add it to the stack
        if (tags.peek().isOpenTag()) {
            htmlTagStack.push(tags.poll());
        } else {
            //closing tag with no opening tag
            if (htmlTagStack.isEmpty()) {
                return null;
            }
            //Next tag in the queue is a closing tag
            //Checks if the closing tag matches the most recent opening tag
            //If it is, remove the tag from the stack
            //If it doesnt match, return the stack
            if (tags.peek().matches(htmlTagStack.peek())) {
                htmlTagStack.pop();
                tags.poll();
            } else {
                return htmlTagStack;
            }
        }
    }
        return htmlTagStack;
    }

}

