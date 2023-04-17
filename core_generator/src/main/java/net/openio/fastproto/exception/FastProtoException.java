/**
 * Licensed to the OpenIO.Net under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.openio.fastproto.exception;


public class FastProtoException extends RuntimeException {
	
	public FastProtoException() {
	}
	
	public FastProtoException(String message) {
		super(message);
	}
	
	public static class AttributeNameConflictException extends FastProtoException {
		
		public AttributeNameConflictException() {
		}
		
		public AttributeNameConflictException(String message) {
			super(message);
		}
	}
	
	
	public static class FailToMakeDirException extends FastProtoException {
		public FailToMakeDirException() {
		}
		
		public FailToMakeDirException(String message) {
			super(message);
		}
	}
	
	public static class FailToCreateFileException extends FastProtoException {
		public FailToCreateFileException() {
		}
		
		public FailToCreateFileException(String message) {
			super(message);
		}
	}

	
	
}
