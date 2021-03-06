/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.tracing.opentelemetry;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;

import com.alibaba.csp.sentinel.tracing.core.SentinelTracingConstants;
import com.alibaba.csp.sentinel.tracing.core.SentinelTracingProcessorSlotEntryCallback;

/**
 * Sentinel for OpenTelemetry ProcessorSlotEntryCallback
 *
 * @author Haojun Ren
 * @since 1.8.1
 */
public class SentinelOpenTelemetryProcessorSlotEntryCallback extends SentinelTracingProcessorSlotEntryCallback<Span> {
    public static final String INSTRUMENTATION_NAME = "opentelemetry.trace.tracer.name";

    private String instrumentationName = System.getProperty(INSTRUMENTATION_NAME, SentinelTracingConstants.TRACER_NAME);

    @Override
    protected Span buildSpan() {
        return GlobalOpenTelemetry.getTracer(instrumentationName).spanBuilder(SentinelTracingConstants.SPAN_NAME).startSpan();
    }

    @Override
    protected void outputSpan(Span span, String key, String value) {
        span.setAttribute(key, value);
    }

    @Override
    protected void finishSpan(Span span) {
        span.end();
    }
}