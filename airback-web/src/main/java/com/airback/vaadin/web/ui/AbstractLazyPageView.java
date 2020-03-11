/**
 * Copyright © airback
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.airback.vaadin.web.ui;

import com.hp.gagawa.java.elements.Div;
import com.airback.core.airbackException;
import com.airback.vaadin.AsyncInvoker;
import com.airback.vaadin.mvp.AbstractVerticalPageView;
import com.airback.vaadin.mvp.LazyPageView;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.vaadin.viritin.layouts.MWindow;

/**
 * @author airback Ltd.
 * @since 4.1.2
 */
public abstract class AbstractLazyPageView extends AbstractVerticalPageView implements LazyPageView {
    private static final long serialVersionUID = 1L;

    private boolean isRunning = false;
    private ProgressIndicator progressIndicator = null;

    @Override
    public void lazyLoadView() {
        if (!isRunning) {
            this.removeAllComponents();
            isRunning = true;
            UI ui = getUI();
            AsyncInvoker.access(ui, new AsyncInvoker.PageCommand() {
                @Override
                public void run() {
                    progressIndicator = new ProgressIndicator();
                    ui.addWindow(progressIndicator);
                }

                @Override
                public void postRun() {
                    try {
                        displayView();
                    } catch (Exception e) {
                        throw new airbackException(e);
                    }
                }

                @Override
                public void cleanUp() {
                    ui.removeWindow(progressIndicator);
                    isRunning = false;
                }
            });
        }
    }

    abstract protected void displayView();

    private static class ProgressIndicator extends MWindow {
        private static final long serialVersionUID = -6157950150738214354L;

        ProgressIndicator() {
            this.withDraggable(false).withClosable(false).withModal(true).withCenter().withStyleName("lazyload-progress");

            Div div = new Div().appendChild(new Div().setCSSClass("sk-cube sk-cube1"))
                    .appendChild(new Div().setCSSClass("sk-cube sk-cube2"))
                    .appendChild(new Div().setCSSClass("sk-cube sk-cube3"))
                    .appendChild(new Div().setCSSClass("sk-cube sk-cube4"))
                    .appendChild(new Div().setCSSClass("sk-cube sk-cube5"))
                    .appendChild(new Div().setCSSClass("sk-cube sk-cube6"))
                    .appendChild(new Div().setCSSClass("sk-cube sk-cube7"))
                    .appendChild(new Div().setCSSClass("sk-cube sk-cube8"))
                    .appendChild(new Div().setCSSClass("sk-cube sk-cube9"));
            Label loadingIcon = new Label(div.write(), ContentMode.HTML);
            loadingIcon.addStyleName("sk-cube-grid");
            this.setContent(loadingIcon);
        }
    }
}
