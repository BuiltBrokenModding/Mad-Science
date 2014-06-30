package madscience.util;

import universalelectricity.api.vector.Vector2;

class Region2
{
        private Vector2 min;
        private Vector2 max;

        public Region2()
        {
                this(new Vector2(), new Vector2());
        }

        private Region2(Vector2 min, Vector2 max)
        {
                this.min = min;
                this.max = max;
        }

        /**
         * Checks if a point is located inside a region
         */
        boolean isIn(Vector2 point)
        {
                return (point.x > this.min.x && point.x < this.max.x) && (point.y > this.min.y && point.y < this.max.y);
        }

        
}
