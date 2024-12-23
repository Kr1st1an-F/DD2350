public class LinkedList {
        public Edge first;
        public Edge last;

        public LinkedList() {

        }

        public void add(Edge ge) {
            if (first == null) {
                first = ge;
                last = ge;
            } else {
                last.next = ge;
                last = ge;
            }
        }

        public void clear() {
            first = null;
            last = null;
        }

        public boolean contains(Edge ge) {
            Edge g = first;
            while (g != null) {
                if (g == ge)
                    return true;
                g = g.next;
            }
            return false;
        }

        public void remove(Edge ge) {
            Edge prev = null;
            Edge g = first;
            while (g != null) {
                if (g == ge) {
                    if (prev == null) {
                        first = g.next;
                    } else {
                        prev.next = g.next;
                    }
                    return;
                }
                prev = g;
                g = g.next;
            }
        }

    }
