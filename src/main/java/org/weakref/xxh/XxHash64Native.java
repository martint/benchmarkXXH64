/*
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
package org.weakref.xxh;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

public class XxHash64Native
{
    private final static MethodHandle XXH64;

    static {
        SymbolLookup lookup = SymbolLookup.libraryLookup("libxxhash.dylib", Arena.ofAuto());

        // XXH_PUBLIC_API XXH64_hash_t XXH64(const void* input, size_t length, unsigned long long seed);
        FunctionDescriptor descriptor = FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG);
        XXH64 = lookup.find("XXH64")
                .map(location -> Linker.nativeLinker().downcallHandle(location, descriptor, Linker.Option.critical(true)))
                .get();
    }

    public static long hash(MemorySegment data)
    {
        try {
            return (long) XXH64.invokeExact(data, data.byteSize(), 0L);
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
