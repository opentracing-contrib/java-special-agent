/* Copyright 2019 The OpenTracing Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opentracing.contrib.specialagent.rule.cxf;

import org.apache.cxf.endpoint.AbstractEndpointFactory;
import org.apache.cxf.tracing.opentracing.OpenTracingClientFeature;
import org.apache.cxf.tracing.opentracing.OpenTracingFeature;
import io.opentracing.util.GlobalTracer;

public class CxfAgentIntercept {

  public static void addClientTracingFeature(final Object thiz) {
    final AbstractEndpointFactory factory = (AbstractEndpointFactory) thiz;
    factory.getFeatures().add(new OpenTracingClientFeature(GlobalTracer.get()));
  }

  public static void addServerTracingFeauture(final Object thiz) {
    final AbstractEndpointFactory factory = (AbstractEndpointFactory) thiz;
    factory.getFeatures().add(new OpenTracingFeature(GlobalTracer.get()));
  }
}