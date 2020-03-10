/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.gnuhpc.interview.designpattern.callback.eda;


import org.gnuhpc.interview.designpattern.callback.eda.event.UserCreatedEvent;
import org.gnuhpc.interview.designpattern.callback.eda.event.UserUpdatedEvent;
import org.gnuhpc.interview.designpattern.callback.eda.framework.EventDispatcher;
import org.gnuhpc.interview.designpattern.callback.eda.handler.UserCreatedEventHandler;
import org.gnuhpc.interview.designpattern.callback.eda.handler.UserUpdatedEventHandler;
import org.gnuhpc.interview.designpattern.callback.eda.model.User;

/**
 * An event-driven architecture (EDA) is a framework that orchestrates behavior around the
 * production, detection and consumption of events as well as the responses they evoke. An event is
 * any identifiable occurrence that has significance for system hardware or software. <p> The
 * example below uses an {@link EventDispatcher} to link/register {@link Event} objects to their
 * respective handlers once an {@link Event} is dispatched, it's respective handler is invoked and
 * the {@link Event} is handled accordingly.
 *
 */
public class App {

    /**
     * Once the {@link EventDispatcher} is initialised, handlers related to specific events have to be
     * made known to the dispatcher by registering them. In this case the {@link UserCreatedEvent} is
     * bound to the UserCreatedEventHandler, whilst the {@link UserUpdatedEvent} is bound to the
     * {@link UserUpdatedEventHandler}. The dispatcher can now be called to dispatch specific events.
     * When a user is saved, the {@link UserCreatedEvent} can be dispatched.
     * On the other hand, when a user is updated, {@link UserUpdatedEvent} can be dispatched.
     *
     * 这个是上面simple案例的复杂版本，在注册上按照Class进行分类处理注册，使用了泛型。
     */
    public static void main(String[] args) {

        EventDispatcher dispatcher = new EventDispatcher();
        dispatcher.registerHandler(UserCreatedEvent.class, new UserCreatedEventHandler());
        dispatcher.registerHandler(UserUpdatedEvent.class, new UserUpdatedEventHandler());

        User user = new User("iluwatar");
        dispatcher.dispatch(new UserCreatedEvent(user));
        dispatcher.dispatch(new UserUpdatedEvent(user));
    }

}
