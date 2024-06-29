import org.weakref.xxh.XxHash64MemorySegment;
import org.weakref.xxh.XxHash64Unsafe;

import java.lang.foreign.MemorySegment;
import java.util.concurrent.ThreadLocalRandom;/*
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
void main()
{
    byte[] data = new byte[16384];
    ThreadLocalRandom.current().nextBytes(data);

    System.out.println(XxHash64Unsafe.hash(data));
    System.out.println(XxHash64MemorySegment.hash(MemorySegment.ofArray(data)));
}
