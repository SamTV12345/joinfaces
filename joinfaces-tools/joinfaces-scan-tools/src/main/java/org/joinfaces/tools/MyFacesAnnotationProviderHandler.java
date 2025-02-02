/*
 * Copyright 2016-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.joinfaces.tools;


/**
 * {@link ScanResultHandler} for the {@link org.apache.myfaces.spi.AnnotationProvider}-SPI.
 *
 * @author Lars Grefer
 */
public class MyFacesAnnotationProviderHandler extends FacesAnnotationProviderHandler {

	private static final String MYFACES_ANNOTATION_PROVIDER = "org.apache.myfaces.spi.AnnotationProvider";

	public MyFacesAnnotationProviderHandler() {
		super(MYFACES_ANNOTATION_PROVIDER);
	}

}
