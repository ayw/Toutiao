package shuo.laoma.collection.c52;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyMap<K, V> extends AbstractMap<K, V> {
    private DynamicArray<Entry<K, V>> darr;
    private Set<Entry<K, V>> entrySet = null;

    public MyMap() {
        darr = new DynamicArray<>();
    }

    public MyMap(Map<? extends K, ? extends V> m) {
        this();
        putAll(m);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> es = entrySet;
        return es != null ? es : (entrySet = new EntrySet());
    }

    @Override
    public V put(K key, V value) {
        for (int i = 0; i < darr.size(); i++) {
            Entry<K, V> entry = darr.get(i);
            if ((key == null && entry.getKey() == null)
                    || (key != null && key.equals(entry.getKey()))) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        Entry<K, V> newEntry = new SimpleEntry<>(key, value);
        darr.add(newEntry);
        return null;
    }

    class EntrySet extends AbstractSet<Entry<K, V>> {
        public Iterator<Entry<K, V>> iterator() {
            return new DynamicArrayIterator<Entry<K, V>>(darr);
        }

        public int size() {
            return darr.size();
        }
    }
}
