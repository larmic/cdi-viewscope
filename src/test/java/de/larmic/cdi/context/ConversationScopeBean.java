/**
 * Copyright 2012 Lars Michaelis
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package de.larmic.cdi.context;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

@SuppressWarnings("serial")
@ConversationScoped
@Named
public class ConversationScopeBean extends AbstractTestBean {

}
