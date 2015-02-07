package madscience.util;


import com.builtbroken.mc.lib.transform.vector.Point;

public class Region2
{
        public Point min;
        public Point max;

        public Region2()
        {
                this(new Point(), new Point());
        }

        public Region2(Point min, Point max)
        {
                this.min = min;
                this.max = max;
        }

        /**
         * Checks if a point is located inside a region
         */
        public boolean isIn(Point point)
        {
                return (point.x() > this.min.x() && point.x() < this.max.x()) && (point.y() > this.min.y() && point.y() < this.max.y());
        }

        /**
         * Returns whether the given region intersects with this one.
         */
        public boolean isIn(Region2 region)
        {
                return region.max.x() > this.min.x() && region.min.x() < this.max.x() ? (region.max.y() > this.min.y() && region.min.y() < this.max.y() ? true : false) : false;
        }
}
