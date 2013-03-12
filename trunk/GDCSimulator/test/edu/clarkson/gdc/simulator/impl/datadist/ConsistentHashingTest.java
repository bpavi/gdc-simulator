package edu.clarkson.gdc.simulator.impl.datadist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import edu.clarkson.gdc.simulator.impl.datadist.ConsistentHashing.Position;
import edu.clarkson.gdc.simulator.impl.datadist.ConsistentHashing.PositionHolder;

public class ConsistentHashingTest {

	@Test
	public void testLocate() {
		List<PositionHolder> list = new ArrayList<PositionHolder>();
		assertEquals(0, ConsistentHashing.locate(list, new Position(
				BigDecimal.ONE, "SA"), 0, 1));

		list.add(new PositionHolder(new Position(new BigDecimal("0.1"), "asdf")));
		assertEquals(0, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.01"), "aaa"), 0, list.size()));
		assertEquals(1, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.2"), "aaa"), 0, list.size()));

		list.add(new PositionHolder(null));
		list.add(new PositionHolder(null));
		list.add(new PositionHolder(new Position(new BigDecimal("0.5"), "aaaa")));

		assertEquals(0, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.01"), "aaa"), 0, list.size()));
		assertEquals(4, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.6"), "aaa"), 0, list.size()));
		assertEquals(1, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.3"), "dd"), 0, list.size()));
		assertEquals(1, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.35"), "dd"), 0, list.size()));

		list.add(new PositionHolder(null));

		assertEquals(0, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.01"), "aaa"), 0, list.size()));
		assertEquals(4, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.6"), "aaa"), 0, list.size()));
		assertEquals(1, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.3"), "dd"), 0, list.size()));
		assertEquals(1, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.35"), "dd"), 0, list.size()));

		list.add(0, new PositionHolder(null));

		assertEquals(1, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.01"), "aaa"), 0, list.size()));
		assertEquals(5, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.6"), "aaa"), 0, list.size()));
		assertEquals(2, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.3"), "dd"), 0, list.size()));
		assertEquals(2, ConsistentHashing.locate(list, new Position(
				new BigDecimal("0.35"), "dd"), 0, list.size()));
	}

	@Test
	public void testAdd() {
		ConsistentHashing hashing = new ConsistentHashing();
		hashing.add("abc");
		hashing.add("def");

		assertEquals(2 * hashing.getBase(), hashing.getPositions().size());
	}

	@Test
	public void testRemove() {
		ConsistentHashing hashing = new ConsistentHashing();
		hashing.add("abc");
		hashing.add("def");
		hashing.add("akk");
		hashing.add("dwm");

		hashing.remove("dwm");

		assertEquals(3 * hashing.getBase(), hashing.getPositions().size());
		assertEquals(0, hashing.hole);

		hashing = new ConsistentHashing();
		for (int i = 0; i < 26; i++) {
			hashing.add("a" + i);
		}

		hashing.remove("a0");
		assertEquals(26 * hashing.getBase(), hashing.getPositions().size());
		assertEquals(10, hashing.hole);
	}

	@Test
	public void testHash() {
		ConsistentHashing hashing = new ConsistentHashing();
		BigDecimal val = hashing.hash("abc");
		assertEquals(40, val.scale());
		assertTrue(BigDecimal.ZERO.compareTo(val) <= 0);
		assertTrue(BigDecimal.ONE.compareTo(val) >= 0);
	}

	@Test
	public void testGet() {
		ConsistentHashing hashing = new ConsistentHashing();
		hashing.add("radf");
		hashing.add("ramp");
		hashing.add("doun");
		hashing.add("lattice");
		hashing.add("diamond");
		hashing.add("realworld");
		Set<String> result = hashing.get("fasw", 3);
		assertEquals(3, result.size());
	}

	@Test
	public void testCompress() {
		ConsistentHashing hashing = new ConsistentHashing();
		for (int i = 0; i < 26; i++) {
			hashing.add("a" + i);
		}

		hashing.remove("a0");
		assertEquals(26 * hashing.getBase(), hashing.getPositions().size());
		assertEquals(10, hashing.hole);

		hashing.compress();

		assertEquals(25 * hashing.getBase(), hashing.getPositions().size());
		assertEquals(0, hashing.hole);
	}

}