package sobol.base.random.halton;

import junit.framework.TestCase;

public class TestHalton extends TestCase
{
	public void testVanDerCorputBase2()
	{
		VDCorputSequence sequence = new VDCorputSequence(2);
		assertEquals(0.0000, sequence.next(), 0.001);
		assertEquals(0.5000, sequence.next(), 0.001);
		assertEquals(0.2500, sequence.next(), 0.001);
		assertEquals(0.7500, sequence.next(), 0.001);
		assertEquals(0.1250, sequence.next(), 0.001);
		assertEquals(0.6250, sequence.next(), 0.001);
		assertEquals(0.3750, sequence.next(), 0.001);
		assertEquals(0.8750, sequence.next(), 0.001);
		assertEquals(0.0625, sequence.next(), 0.001);
		assertEquals(0.5625, sequence.next(), 0.001);
		assertEquals(0.3125, sequence.next(), 0.001);
		assertEquals(0.8125, sequence.next(), 0.001);
		assertEquals(0.1875, sequence.next(), 0.001);
		assertEquals(0.6875, sequence.next(), 0.001);
		assertEquals(0.4375, sequence.next(), 0.001);
		assertEquals(0.9375, sequence.next(), 0.001);
		assertEquals(0.0313, sequence.next(), 0.001);
		assertEquals(0.5313, sequence.next(), 0.001);
		assertEquals(0.2813, sequence.next(), 0.001);
		assertEquals(0.7813, sequence.next(), 0.001);
		assertEquals(0.1563, sequence.next(), 0.001);
		assertEquals(0.6563, sequence.next(), 0.001);
		assertEquals(0.4063, sequence.next(), 0.001);
		assertEquals(0.9063, sequence.next(), 0.001);
		assertEquals(0.0938, sequence.next(), 0.001);
		assertEquals(0.5938, sequence.next(), 0.001);
		assertEquals(0.3438, sequence.next(), 0.001);
		assertEquals(0.8438, sequence.next(), 0.001);
		assertEquals(0.2188, sequence.next(), 0.001);
		assertEquals(0.7188, sequence.next(), 0.001);
		assertEquals(0.4688, sequence.next(), 0.001);
		assertEquals(0.9688, sequence.next(), 0.001);
		assertEquals(0.0156, sequence.next(), 0.001);
		assertEquals(0.5156, sequence.next(), 0.001);
		assertEquals(0.2656, sequence.next(), 0.001);
		assertEquals(0.7656, sequence.next(), 0.001);
		assertEquals(0.1406, sequence.next(), 0.001);
		assertEquals(0.6406, sequence.next(), 0.001);
		assertEquals(0.3906, sequence.next(), 0.001);
		assertEquals(0.8906, sequence.next(), 0.001);
		assertEquals(0.0781, sequence.next(), 0.001);
		assertEquals(0.5781, sequence.next(), 0.001);
		assertEquals(0.3281, sequence.next(), 0.001);
		assertEquals(0.8281, sequence.next(), 0.001);
		assertEquals(0.2031, sequence.next(), 0.001);
		assertEquals(0.7031, sequence.next(), 0.001);
		assertEquals(0.4531, sequence.next(), 0.001);
		assertEquals(0.9531, sequence.next(), 0.001);
		assertEquals(0.0469, sequence.next(), 0.001);
		assertEquals(0.5469, sequence.next(), 0.001);
		assertEquals(0.2969, sequence.next(), 0.001);
		assertEquals(0.7969, sequence.next(), 0.001);
		assertEquals(0.1719, sequence.next(), 0.001);
		assertEquals(0.6719, sequence.next(), 0.001);
		assertEquals(0.4219, sequence.next(), 0.001);
		assertEquals(0.9219, sequence.next(), 0.001);
		assertEquals(0.1094, sequence.next(), 0.001);
		assertEquals(0.6094, sequence.next(), 0.001);
		assertEquals(0.3594, sequence.next(), 0.001);
		assertEquals(0.8594, sequence.next(), 0.001);
		assertEquals(0.2344, sequence.next(), 0.001);
		assertEquals(0.7344, sequence.next(), 0.001);
		assertEquals(0.4844, sequence.next(), 0.001);
		assertEquals(0.9844, sequence.next(), 0.001);
		assertEquals(0.0078, sequence.next(), 0.001);
		assertEquals(0.5078, sequence.next(), 0.001);
		assertEquals(0.2578, sequence.next(), 0.001);
		assertEquals(0.7578, sequence.next(), 0.001);
		assertEquals(0.1328, sequence.next(), 0.001);
		assertEquals(0.6328, sequence.next(), 0.001);
		assertEquals(0.3828, sequence.next(), 0.001);
		assertEquals(0.8828, sequence.next(), 0.001);
		assertEquals(0.0703, sequence.next(), 0.001);
		assertEquals(0.5703, sequence.next(), 0.001);
		assertEquals(0.3203, sequence.next(), 0.001);
		assertEquals(0.8203, sequence.next(), 0.001);
		assertEquals(0.1953, sequence.next(), 0.001);
		assertEquals(0.6953, sequence.next(), 0.001);
		assertEquals(0.4453, sequence.next(), 0.001);
		assertEquals(0.9453, sequence.next(), 0.001);
		assertEquals(0.0391, sequence.next(), 0.001);
		assertEquals(0.5391, sequence.next(), 0.001);
		assertEquals(0.2891, sequence.next(), 0.001);
		assertEquals(0.7891, sequence.next(), 0.001);
		assertEquals(0.1641, sequence.next(), 0.001);
		assertEquals(0.6641, sequence.next(), 0.001);
		assertEquals(0.4141, sequence.next(), 0.001);
		assertEquals(0.9141, sequence.next(), 0.001);
		assertEquals(0.1016, sequence.next(), 0.001);
		assertEquals(0.6016, sequence.next(), 0.001);
		assertEquals(0.3516, sequence.next(), 0.001);
		assertEquals(0.8516, sequence.next(), 0.001);
		assertEquals(0.2266, sequence.next(), 0.001);
		assertEquals(0.7266, sequence.next(), 0.001);
		assertEquals(0.4766, sequence.next(), 0.001);
		assertEquals(0.9766, sequence.next(), 0.001);
		assertEquals(0.0234, sequence.next(), 0.001);
		assertEquals(0.5234, sequence.next(), 0.001);
		assertEquals(0.2734, sequence.next(), 0.001);
		assertEquals(0.7734, sequence.next(), 0.001);
		assertEquals(0.1484, sequence.next(), 0.001);
		assertEquals(0.6484, sequence.next(), 0.001);
		assertEquals(0.3984, sequence.next(), 0.001);
		assertEquals(0.8984, sequence.next(), 0.001);
		assertEquals(0.0859, sequence.next(), 0.001);
		assertEquals(0.5859, sequence.next(), 0.001);
		assertEquals(0.3359, sequence.next(), 0.001);
		assertEquals(0.8359, sequence.next(), 0.001);
		assertEquals(0.2109, sequence.next(), 0.001);
		assertEquals(0.7109, sequence.next(), 0.001);
		assertEquals(0.4609, sequence.next(), 0.001);
		assertEquals(0.9609, sequence.next(), 0.001);
		assertEquals(0.0547, sequence.next(), 0.001);
		assertEquals(0.5547, sequence.next(), 0.001);
		assertEquals(0.3047, sequence.next(), 0.001);
		assertEquals(0.8047, sequence.next(), 0.001);
		assertEquals(0.1797, sequence.next(), 0.001);
		assertEquals(0.6797, sequence.next(), 0.001);
		assertEquals(0.4297, sequence.next(), 0.001);
		assertEquals(0.9297, sequence.next(), 0.001);
		assertEquals(0.1172, sequence.next(), 0.001);
		assertEquals(0.6172, sequence.next(), 0.001);
		assertEquals(0.3672, sequence.next(), 0.001);
		assertEquals(0.8672, sequence.next(), 0.001);
		assertEquals(0.2422, sequence.next(), 0.001);
		assertEquals(0.7422, sequence.next(), 0.001);
		assertEquals(0.4922, sequence.next(), 0.001);
		assertEquals(0.9922, sequence.next(), 0.001);
		assertEquals(0.0039, sequence.next(), 0.001);
		assertEquals(0.5039, sequence.next(), 0.001);
		assertEquals(0.2539, sequence.next(), 0.001);
		assertEquals(0.7539, sequence.next(), 0.001);
		assertEquals(0.1289, sequence.next(), 0.001);
		assertEquals(0.6289, sequence.next(), 0.001);
		assertEquals(0.3789, sequence.next(), 0.001);
		assertEquals(0.8789, sequence.next(), 0.001);
		assertEquals(0.0664, sequence.next(), 0.001);
		assertEquals(0.5664, sequence.next(), 0.001);
		assertEquals(0.3164, sequence.next(), 0.001);
		assertEquals(0.8164, sequence.next(), 0.001);
		assertEquals(0.1914, sequence.next(), 0.001);
		assertEquals(0.6914, sequence.next(), 0.001);
		assertEquals(0.4414, sequence.next(), 0.001);
		assertEquals(0.9414, sequence.next(), 0.001);
		assertEquals(0.0352, sequence.next(), 0.001);
		assertEquals(0.5352, sequence.next(), 0.001);
		assertEquals(0.2852, sequence.next(), 0.001);
		assertEquals(0.7852, sequence.next(), 0.001);
		assertEquals(0.1602, sequence.next(), 0.001);
		assertEquals(0.6602, sequence.next(), 0.001);
		assertEquals(0.4102, sequence.next(), 0.001);
		assertEquals(0.9102, sequence.next(), 0.001);
		assertEquals(0.0977, sequence.next(), 0.001);
		assertEquals(0.5977, sequence.next(), 0.001);
		assertEquals(0.3477, sequence.next(), 0.001);
		assertEquals(0.8477, sequence.next(), 0.001);
		assertEquals(0.2227, sequence.next(), 0.001);
		assertEquals(0.7227, sequence.next(), 0.001);
		assertEquals(0.4727, sequence.next(), 0.001);
		assertEquals(0.9727, sequence.next(), 0.001);
		assertEquals(0.0195, sequence.next(), 0.001);
		assertEquals(0.5195, sequence.next(), 0.001);
		assertEquals(0.2695, sequence.next(), 0.001);
		assertEquals(0.7695, sequence.next(), 0.001);
		assertEquals(0.1445, sequence.next(), 0.001);
		assertEquals(0.6445, sequence.next(), 0.001);
		assertEquals(0.3945, sequence.next(), 0.001);
		assertEquals(0.8945, sequence.next(), 0.001);
		assertEquals(0.0820, sequence.next(), 0.001);
		assertEquals(0.5820, sequence.next(), 0.001);
		assertEquals(0.3320, sequence.next(), 0.001);
		assertEquals(0.8320, sequence.next(), 0.001);
		assertEquals(0.2070, sequence.next(), 0.001);
		assertEquals(0.7070, sequence.next(), 0.001);
		assertEquals(0.4570, sequence.next(), 0.001);
		assertEquals(0.9570, sequence.next(), 0.001);
		assertEquals(0.0508, sequence.next(), 0.001);
		assertEquals(0.5508, sequence.next(), 0.001);
		assertEquals(0.3008, sequence.next(), 0.001);
		assertEquals(0.8008, sequence.next(), 0.001);
		assertEquals(0.1758, sequence.next(), 0.001);
		assertEquals(0.6758, sequence.next(), 0.001);
		assertEquals(0.4258, sequence.next(), 0.001);
		assertEquals(0.9258, sequence.next(), 0.001);
		assertEquals(0.1133, sequence.next(), 0.001);
		assertEquals(0.6133, sequence.next(), 0.001);
		assertEquals(0.3633, sequence.next(), 0.001);
		assertEquals(0.8633, sequence.next(), 0.001);
		assertEquals(0.2383, sequence.next(), 0.001);
		assertEquals(0.7383, sequence.next(), 0.001);
		assertEquals(0.4883, sequence.next(), 0.001);
		assertEquals(0.9883, sequence.next(), 0.001);
		assertEquals(0.0117, sequence.next(), 0.001);
		assertEquals(0.5117, sequence.next(), 0.001);
		assertEquals(0.2617, sequence.next(), 0.001);
		assertEquals(0.7617, sequence.next(), 0.001);
		assertEquals(0.1367, sequence.next(), 0.001);
		assertEquals(0.6367, sequence.next(), 0.001);
		assertEquals(0.3867, sequence.next(), 0.001);
		assertEquals(0.8867, sequence.next(), 0.001);
		assertEquals(0.0742, sequence.next(), 0.001);
		assertEquals(0.5742, sequence.next(), 0.001);
		assertEquals(0.3242, sequence.next(), 0.001);
		assertEquals(0.8242, sequence.next(), 0.001);
		assertEquals(0.1992, sequence.next(), 0.001);
		assertEquals(0.6992, sequence.next(), 0.001);
		assertEquals(0.4492, sequence.next(), 0.001);
		assertEquals(0.9492, sequence.next(), 0.001);
		assertEquals(0.0430, sequence.next(), 0.001);
		assertEquals(0.5430, sequence.next(), 0.001);
		assertEquals(0.2930, sequence.next(), 0.001);
		assertEquals(0.7930, sequence.next(), 0.001);
		assertEquals(0.1680, sequence.next(), 0.001);
		assertEquals(0.6680, sequence.next(), 0.001);
		assertEquals(0.4180, sequence.next(), 0.001);
		assertEquals(0.9180, sequence.next(), 0.001);
		assertEquals(0.1055, sequence.next(), 0.001);
		assertEquals(0.6055, sequence.next(), 0.001);
		assertEquals(0.3555, sequence.next(), 0.001);
		assertEquals(0.8555, sequence.next(), 0.001);
		assertEquals(0.2305, sequence.next(), 0.001);
		assertEquals(0.7305, sequence.next(), 0.001);
		assertEquals(0.4805, sequence.next(), 0.001);
		assertEquals(0.9805, sequence.next(), 0.001);
		assertEquals(0.0273, sequence.next(), 0.001);
		assertEquals(0.5273, sequence.next(), 0.001);
		assertEquals(0.2773, sequence.next(), 0.001);
		assertEquals(0.7773, sequence.next(), 0.001);
		assertEquals(0.1523, sequence.next(), 0.001);
		assertEquals(0.6523, sequence.next(), 0.001);
		assertEquals(0.4023, sequence.next(), 0.001);
		assertEquals(0.9023, sequence.next(), 0.001);
		assertEquals(0.0898, sequence.next(), 0.001);
		assertEquals(0.5898, sequence.next(), 0.001);
		assertEquals(0.3398, sequence.next(), 0.001);
		assertEquals(0.8398, sequence.next(), 0.001);
		assertEquals(0.2148, sequence.next(), 0.001);
		assertEquals(0.7148, sequence.next(), 0.001);
		assertEquals(0.4648, sequence.next(), 0.001);
		assertEquals(0.9648, sequence.next(), 0.001);
		assertEquals(0.0586, sequence.next(), 0.001);
		assertEquals(0.5586, sequence.next(), 0.001);
		assertEquals(0.3086, sequence.next(), 0.001);
		assertEquals(0.8086, sequence.next(), 0.001);
		assertEquals(0.1836, sequence.next(), 0.001);
		assertEquals(0.6836, sequence.next(), 0.001);
		assertEquals(0.4336, sequence.next(), 0.001);
		assertEquals(0.9336, sequence.next(), 0.001);
		assertEquals(0.1211, sequence.next(), 0.001);
		assertEquals(0.6211, sequence.next(), 0.001);
		assertEquals(0.3711, sequence.next(), 0.001);
		assertEquals(0.8711, sequence.next(), 0.001);
		assertEquals(0.2461, sequence.next(), 0.001);
		assertEquals(0.7461, sequence.next(), 0.001);
		assertEquals(0.4961, sequence.next(), 0.001);
		assertEquals(0.9961, sequence.next(), 0.001);
	}

	public void testVanDerCorputBase7()
	{
		VDCorputSequence sequence = new VDCorputSequence(7);
		assertEquals(0.0000, sequence.next(), 0.001);
		assertEquals(0.1429, sequence.next(), 0.001);
		assertEquals(0.2857, sequence.next(), 0.001);
		assertEquals(0.4286, sequence.next(), 0.001);
		assertEquals(0.5714, sequence.next(), 0.001);
		assertEquals(0.7143, sequence.next(), 0.001);
		assertEquals(0.8571, sequence.next(), 0.001);
		assertEquals(0.0204, sequence.next(), 0.001);
		assertEquals(0.1633, sequence.next(), 0.001);
		assertEquals(0.3061, sequence.next(), 0.001);
		assertEquals(0.4490, sequence.next(), 0.001);
		assertEquals(0.5918, sequence.next(), 0.001);
		assertEquals(0.7347, sequence.next(), 0.001);
		assertEquals(0.8776, sequence.next(), 0.001);
		assertEquals(0.0408, sequence.next(), 0.001);
		assertEquals(0.1837, sequence.next(), 0.001);
		assertEquals(0.3265, sequence.next(), 0.001);
		assertEquals(0.4694, sequence.next(), 0.001);
		assertEquals(0.6122, sequence.next(), 0.001);
		assertEquals(0.7551, sequence.next(), 0.001);
		assertEquals(0.8980, sequence.next(), 0.001);
		assertEquals(0.0612, sequence.next(), 0.001);
		assertEquals(0.2041, sequence.next(), 0.001);
		assertEquals(0.3469, sequence.next(), 0.001);
		assertEquals(0.4898, sequence.next(), 0.001);
		assertEquals(0.6327, sequence.next(), 0.001);
		assertEquals(0.7755, sequence.next(), 0.001);
		assertEquals(0.9184, sequence.next(), 0.001);
		assertEquals(0.0816, sequence.next(), 0.001);
		assertEquals(0.2245, sequence.next(), 0.001);
		assertEquals(0.3673, sequence.next(), 0.001);
		assertEquals(0.5102, sequence.next(), 0.001);
		assertEquals(0.6531, sequence.next(), 0.001);
		assertEquals(0.7959, sequence.next(), 0.001);
		assertEquals(0.9388, sequence.next(), 0.001);
		assertEquals(0.1020, sequence.next(), 0.001);
		assertEquals(0.2449, sequence.next(), 0.001);
		assertEquals(0.3878, sequence.next(), 0.001);
		assertEquals(0.5306, sequence.next(), 0.001);
		assertEquals(0.6735, sequence.next(), 0.001);
		assertEquals(0.8163, sequence.next(), 0.001);
		assertEquals(0.9592, sequence.next(), 0.001);
		assertEquals(0.1224, sequence.next(), 0.001);
		assertEquals(0.2653, sequence.next(), 0.001);
		assertEquals(0.4082, sequence.next(), 0.001);
		assertEquals(0.5510, sequence.next(), 0.001);
		assertEquals(0.6939, sequence.next(), 0.001);
		assertEquals(0.8367, sequence.next(), 0.001);
		assertEquals(0.9796, sequence.next(), 0.001);
		assertEquals(0.0029, sequence.next(), 0.001);
		assertEquals(0.1458, sequence.next(), 0.001);
		assertEquals(0.2886, sequence.next(), 0.001);
		assertEquals(0.4315, sequence.next(), 0.001);
		assertEquals(0.5743, sequence.next(), 0.001);
		assertEquals(0.7172, sequence.next(), 0.001);
		assertEquals(0.8601, sequence.next(), 0.001);
		assertEquals(0.0233, sequence.next(), 0.001);
		assertEquals(0.1662, sequence.next(), 0.001);
		assertEquals(0.3090, sequence.next(), 0.001);
		assertEquals(0.4519, sequence.next(), 0.001);
		assertEquals(0.5948, sequence.next(), 0.001);
		assertEquals(0.7376, sequence.next(), 0.001);
		assertEquals(0.8805, sequence.next(), 0.001);
		assertEquals(0.0437, sequence.next(), 0.001);
		assertEquals(0.1866, sequence.next(), 0.001);
		assertEquals(0.3294, sequence.next(), 0.001);
		assertEquals(0.4723, sequence.next(), 0.001);
		assertEquals(0.6152, sequence.next(), 0.001);
		assertEquals(0.7580, sequence.next(), 0.001);
		assertEquals(0.9009, sequence.next(), 0.001);
		assertEquals(0.0641, sequence.next(), 0.001);
		assertEquals(0.2070, sequence.next(), 0.001);
		assertEquals(0.3499, sequence.next(), 0.001);
		assertEquals(0.4927, sequence.next(), 0.001);
		assertEquals(0.6356, sequence.next(), 0.001);
		assertEquals(0.7784, sequence.next(), 0.001);
		assertEquals(0.9213, sequence.next(), 0.001);
		assertEquals(0.0845, sequence.next(), 0.001);
		assertEquals(0.2274, sequence.next(), 0.001);
		assertEquals(0.3703, sequence.next(), 0.001);
		assertEquals(0.5131, sequence.next(), 0.001);
		assertEquals(0.6560, sequence.next(), 0.001);
		assertEquals(0.7988, sequence.next(), 0.001);
		assertEquals(0.9417, sequence.next(), 0.001);
		assertEquals(0.1050, sequence.next(), 0.001);
		assertEquals(0.2478, sequence.next(), 0.001);
		assertEquals(0.3907, sequence.next(), 0.001);
		assertEquals(0.5335, sequence.next(), 0.001);
		assertEquals(0.6764, sequence.next(), 0.001);
		assertEquals(0.8192, sequence.next(), 0.001);
		assertEquals(0.9621, sequence.next(), 0.001);
		assertEquals(0.1254, sequence.next(), 0.001);
		assertEquals(0.2682, sequence.next(), 0.001);
		assertEquals(0.4111, sequence.next(), 0.001);
		assertEquals(0.5539, sequence.next(), 0.001);
		assertEquals(0.6968, sequence.next(), 0.001);
		assertEquals(0.8397, sequence.next(), 0.001);
		assertEquals(0.9825, sequence.next(), 0.001);
		assertEquals(0.0058, sequence.next(), 0.001);
		assertEquals(0.1487, sequence.next(), 0.001);
		assertEquals(0.2915, sequence.next(), 0.001);
		assertEquals(0.4344, sequence.next(), 0.001);
		assertEquals(0.5773, sequence.next(), 0.001);
		assertEquals(0.7201, sequence.next(), 0.001);
		assertEquals(0.8630, sequence.next(), 0.001);
		assertEquals(0.0262, sequence.next(), 0.001);
		assertEquals(0.1691, sequence.next(), 0.001);
		assertEquals(0.3120, sequence.next(), 0.001);
		assertEquals(0.4548, sequence.next(), 0.001);
		assertEquals(0.5977, sequence.next(), 0.001);
		assertEquals(0.7405, sequence.next(), 0.001);
		assertEquals(0.8834, sequence.next(), 0.001);
		assertEquals(0.0466, sequence.next(), 0.001);
		assertEquals(0.1895, sequence.next(), 0.001);
		assertEquals(0.3324, sequence.next(), 0.001);
		assertEquals(0.4752, sequence.next(), 0.001);
		assertEquals(0.6181, sequence.next(), 0.001);
		assertEquals(0.7609, sequence.next(), 0.001);
		assertEquals(0.9038, sequence.next(), 0.001);
		assertEquals(0.0671, sequence.next(), 0.001);
		assertEquals(0.2099, sequence.next(), 0.001);
		assertEquals(0.3528, sequence.next(), 0.001);
		assertEquals(0.4956, sequence.next(), 0.001);
		assertEquals(0.6385, sequence.next(), 0.001);
		assertEquals(0.7813, sequence.next(), 0.001);
		assertEquals(0.9242, sequence.next(), 0.001);
		assertEquals(0.0875, sequence.next(), 0.001);
		assertEquals(0.2303, sequence.next(), 0.001);
		assertEquals(0.3732, sequence.next(), 0.001);
		assertEquals(0.5160, sequence.next(), 0.001);
		assertEquals(0.6589, sequence.next(), 0.001);
		assertEquals(0.8017, sequence.next(), 0.001);
		assertEquals(0.9446, sequence.next(), 0.001);
		assertEquals(0.1079, sequence.next(), 0.001);
		assertEquals(0.2507, sequence.next(), 0.001);
		assertEquals(0.3936, sequence.next(), 0.001);
		assertEquals(0.5364, sequence.next(), 0.001);
		assertEquals(0.6793, sequence.next(), 0.001);
		assertEquals(0.8222, sequence.next(), 0.001);
		assertEquals(0.9650, sequence.next(), 0.001);
		assertEquals(0.1283, sequence.next(), 0.001);
		assertEquals(0.2711, sequence.next(), 0.001);
		assertEquals(0.4140, sequence.next(), 0.001);
		assertEquals(0.5569, sequence.next(), 0.001);
		assertEquals(0.6997, sequence.next(), 0.001);
		assertEquals(0.8426, sequence.next(), 0.001);
		assertEquals(0.9854, sequence.next(), 0.001);
		assertEquals(0.0087, sequence.next(), 0.001);
		assertEquals(0.1516, sequence.next(), 0.001);
		assertEquals(0.2945, sequence.next(), 0.001);
		assertEquals(0.4373, sequence.next(), 0.001);
		assertEquals(0.5802, sequence.next(), 0.001);
		assertEquals(0.7230, sequence.next(), 0.001);
		assertEquals(0.8659, sequence.next(), 0.001);
		assertEquals(0.0292, sequence.next(), 0.001);
		assertEquals(0.1720, sequence.next(), 0.001);
		assertEquals(0.3149, sequence.next(), 0.001);
		assertEquals(0.4577, sequence.next(), 0.001);
		assertEquals(0.6006, sequence.next(), 0.001);
		assertEquals(0.7434, sequence.next(), 0.001);
		assertEquals(0.8863, sequence.next(), 0.001);
		assertEquals(0.0496, sequence.next(), 0.001);
		assertEquals(0.1924, sequence.next(), 0.001);
		assertEquals(0.3353, sequence.next(), 0.001);
		assertEquals(0.4781, sequence.next(), 0.001);
		assertEquals(0.6210, sequence.next(), 0.001);
		assertEquals(0.7638, sequence.next(), 0.001);
		assertEquals(0.9067, sequence.next(), 0.001);
		assertEquals(0.0700, sequence.next(), 0.001);
		assertEquals(0.2128, sequence.next(), 0.001);
		assertEquals(0.3557, sequence.next(), 0.001);
		assertEquals(0.4985, sequence.next(), 0.001);
		assertEquals(0.6414, sequence.next(), 0.001);
		assertEquals(0.7843, sequence.next(), 0.001);
		assertEquals(0.9271, sequence.next(), 0.001);
		assertEquals(0.0904, sequence.next(), 0.001);
		assertEquals(0.2332, sequence.next(), 0.001);
		assertEquals(0.3761, sequence.next(), 0.001);
		assertEquals(0.5190, sequence.next(), 0.001);
		assertEquals(0.6618, sequence.next(), 0.001);
		assertEquals(0.8047, sequence.next(), 0.001);
		assertEquals(0.9475, sequence.next(), 0.001);
		assertEquals(0.1108, sequence.next(), 0.001);
		assertEquals(0.2536, sequence.next(), 0.001);
		assertEquals(0.3965, sequence.next(), 0.001);
		assertEquals(0.5394, sequence.next(), 0.001);
		assertEquals(0.6822, sequence.next(), 0.001);
		assertEquals(0.8251, sequence.next(), 0.001);
		assertEquals(0.9679, sequence.next(), 0.001);
		assertEquals(0.1312, sequence.next(), 0.001);
		assertEquals(0.2741, sequence.next(), 0.001);
		assertEquals(0.4169, sequence.next(), 0.001);
		assertEquals(0.5598, sequence.next(), 0.001);
		assertEquals(0.7026, sequence.next(), 0.001);
		assertEquals(0.8455, sequence.next(), 0.001);
		assertEquals(0.9883, sequence.next(), 0.001);
		assertEquals(0.0117, sequence.next(), 0.001);
		assertEquals(0.1545, sequence.next(), 0.001);
		assertEquals(0.2974, sequence.next(), 0.001);
		assertEquals(0.4402, sequence.next(), 0.001);
		assertEquals(0.5831, sequence.next(), 0.001);
		assertEquals(0.7259, sequence.next(), 0.001);
		assertEquals(0.8688, sequence.next(), 0.001);
		assertEquals(0.0321, sequence.next(), 0.001);
		assertEquals(0.1749, sequence.next(), 0.001);
		assertEquals(0.3178, sequence.next(), 0.001);
		assertEquals(0.4606, sequence.next(), 0.001);
		assertEquals(0.6035, sequence.next(), 0.001);
		assertEquals(0.7464, sequence.next(), 0.001);
		assertEquals(0.8892, sequence.next(), 0.001);
		assertEquals(0.0525, sequence.next(), 0.001);
		assertEquals(0.1953, sequence.next(), 0.001);
		assertEquals(0.3382, sequence.next(), 0.001);
		assertEquals(0.4810, sequence.next(), 0.001);
		assertEquals(0.6239, sequence.next(), 0.001);
		assertEquals(0.7668, sequence.next(), 0.001);
		assertEquals(0.9096, sequence.next(), 0.001);
		assertEquals(0.0729, sequence.next(), 0.001);
		assertEquals(0.2157, sequence.next(), 0.001);
		assertEquals(0.3586, sequence.next(), 0.001);
		assertEquals(0.5015, sequence.next(), 0.001);
		assertEquals(0.6443, sequence.next(), 0.001);
		assertEquals(0.7872, sequence.next(), 0.001);
		assertEquals(0.9300, sequence.next(), 0.001);
		assertEquals(0.0933, sequence.next(), 0.001);
		assertEquals(0.2362, sequence.next(), 0.001);
		assertEquals(0.3790, sequence.next(), 0.001);
		assertEquals(0.5219, sequence.next(), 0.001);
		assertEquals(0.6647, sequence.next(), 0.001);
		assertEquals(0.8076, sequence.next(), 0.001);
		assertEquals(0.9504, sequence.next(), 0.001);
		assertEquals(0.1137, sequence.next(), 0.001);
		assertEquals(0.2566, sequence.next(), 0.001);
		assertEquals(0.3994, sequence.next(), 0.001);
		assertEquals(0.5423, sequence.next(), 0.001);
		assertEquals(0.6851, sequence.next(), 0.001);
		assertEquals(0.8280, sequence.next(), 0.001);
		assertEquals(0.9708, sequence.next(), 0.001);
		assertEquals(0.1341, sequence.next(), 0.001);
		assertEquals(0.2770, sequence.next(), 0.001);
		assertEquals(0.4198, sequence.next(), 0.001);
		assertEquals(0.5627, sequence.next(), 0.001);
		assertEquals(0.7055, sequence.next(), 0.001);
		assertEquals(0.8484, sequence.next(), 0.001);
		assertEquals(0.9913, sequence.next(), 0.001);
		assertEquals(0.0146, sequence.next(), 0.001);
		assertEquals(0.1574, sequence.next(), 0.001);
		assertEquals(0.3003, sequence.next(), 0.001);
		assertEquals(0.4431, sequence.next(), 0.001);
		assertEquals(0.5860, sequence.next(), 0.001);
		assertEquals(0.7289, sequence.next(), 0.001);
		assertEquals(0.8717, sequence.next(), 0.001);
		assertEquals(0.0350, sequence.next(), 0.001);
		assertEquals(0.1778, sequence.next(), 0.001);
		assertEquals(0.3207, sequence.next(), 0.001);
		assertEquals(0.4636, sequence.next(), 0.001);
	}

	public void testHaltonTwoDimensions()
	{
		HaltonRandomGenerator sequence = new HaltonRandomGenerator(2);
		double[] data = null;
		
		data = sequence.randDouble();
		assertEquals(0.0000, data[0], 0.001);
		assertEquals(0.0000, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5000, data[0], 0.001);
		assertEquals(0.3333, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2500, data[0], 0.001);
		assertEquals(0.6667, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7500, data[0], 0.001);
		assertEquals(0.1111, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1250, data[0], 0.001);
		assertEquals(0.4444, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6250, data[0], 0.001);
		assertEquals(0.7778, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3750, data[0], 0.001);
		assertEquals(0.2222, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8750, data[0], 0.001);
		assertEquals(0.5556, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0625, data[0], 0.001);
		assertEquals(0.8889, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5625, data[0], 0.001);
		assertEquals(0.0370, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3125, data[0], 0.001);
		assertEquals(0.3704, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8125, data[0], 0.001);
		assertEquals(0.7037, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1875, data[0], 0.001);
		assertEquals(0.1481, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6875, data[0], 0.001);
		assertEquals(0.4815, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4375, data[0], 0.001);
		assertEquals(0.8148, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9375, data[0], 0.001);
		assertEquals(0.2593, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0313, data[0], 0.001);
		assertEquals(0.5926, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5313, data[0], 0.001);
		assertEquals(0.9259, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2813, data[0], 0.001);
		assertEquals(0.0741, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7813, data[0], 0.001);
		assertEquals(0.4074, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1563, data[0], 0.001);
		assertEquals(0.7407, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6563, data[0], 0.001);
		assertEquals(0.1852, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4063, data[0], 0.001);
		assertEquals(0.5185, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9063, data[0], 0.001);
		assertEquals(0.8519, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0938, data[0], 0.001);
		assertEquals(0.2963, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5938, data[0], 0.001);
		assertEquals(0.6296, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3438, data[0], 0.001);
		assertEquals(0.9630, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8438, data[0], 0.001);
		assertEquals(0.0123, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2188, data[0], 0.001);
		assertEquals(0.3457, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7188, data[0], 0.001);
		assertEquals(0.6790, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4688, data[0], 0.001);
		assertEquals(0.1235, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9688, data[0], 0.001);
		assertEquals(0.4568, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0156, data[0], 0.001);
		assertEquals(0.7901, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5156, data[0], 0.001);
		assertEquals(0.2346, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2656, data[0], 0.001);
		assertEquals(0.5679, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7656, data[0], 0.001);
		assertEquals(0.9012, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1406, data[0], 0.001);
		assertEquals(0.0494, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6406, data[0], 0.001);
		assertEquals(0.3827, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3906, data[0], 0.001);
		assertEquals(0.7160, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8906, data[0], 0.001);
		assertEquals(0.1605, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0781, data[0], 0.001);
		assertEquals(0.4938, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5781, data[0], 0.001);
		assertEquals(0.8272, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3281, data[0], 0.001);
		assertEquals(0.2716, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8281, data[0], 0.001);
		assertEquals(0.6049, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2031, data[0], 0.001);
		assertEquals(0.9383, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7031, data[0], 0.001);
		assertEquals(0.0864, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4531, data[0], 0.001);
		assertEquals(0.4198, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9531, data[0], 0.001);
		assertEquals(0.7531, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0469, data[0], 0.001);
		assertEquals(0.1975, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5469, data[0], 0.001);
		assertEquals(0.5309, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2969, data[0], 0.001);
		assertEquals(0.8642, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7969, data[0], 0.001);
		assertEquals(0.3086, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1719, data[0], 0.001);
		assertEquals(0.6420, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6719, data[0], 0.001);
		assertEquals(0.9753, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4219, data[0], 0.001);
		assertEquals(0.0247, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9219, data[0], 0.001);
		assertEquals(0.3580, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1094, data[0], 0.001);
		assertEquals(0.6914, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6094, data[0], 0.001);
		assertEquals(0.1358, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3594, data[0], 0.001);
		assertEquals(0.4691, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8594, data[0], 0.001);
		assertEquals(0.8025, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2344, data[0], 0.001);
		assertEquals(0.2469, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7344, data[0], 0.001);
		assertEquals(0.5802, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4844, data[0], 0.001);
		assertEquals(0.9136, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9844, data[0], 0.001);
		assertEquals(0.0617, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0078, data[0], 0.001);
		assertEquals(0.3951, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5078, data[0], 0.001);
		assertEquals(0.7284, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2578, data[0], 0.001);
		assertEquals(0.1728, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7578, data[0], 0.001);
		assertEquals(0.5062, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1328, data[0], 0.001);
		assertEquals(0.8395, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6328, data[0], 0.001);
		assertEquals(0.2840, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3828, data[0], 0.001);
		assertEquals(0.6173, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8828, data[0], 0.001);
		assertEquals(0.9506, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0703, data[0], 0.001);
		assertEquals(0.0988, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5703, data[0], 0.001);
		assertEquals(0.4321, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3203, data[0], 0.001);
		assertEquals(0.7654, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8203, data[0], 0.001);
		assertEquals(0.2099, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1953, data[0], 0.001);
		assertEquals(0.5432, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6953, data[0], 0.001);
		assertEquals(0.8765, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4453, data[0], 0.001);
		assertEquals(0.3210, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9453, data[0], 0.001);
		assertEquals(0.6543, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0391, data[0], 0.001);
		assertEquals(0.9877, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5391, data[0], 0.001);
		assertEquals(0.0041, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2891, data[0], 0.001);
		assertEquals(0.3374, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7891, data[0], 0.001);
		assertEquals(0.6708, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1641, data[0], 0.001);
		assertEquals(0.1152, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6641, data[0], 0.001);
		assertEquals(0.4486, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4141, data[0], 0.001);
		assertEquals(0.7819, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9141, data[0], 0.001);
		assertEquals(0.2263, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1016, data[0], 0.001);
		assertEquals(0.5597, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6016, data[0], 0.001);
		assertEquals(0.8930, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3516, data[0], 0.001);
		assertEquals(0.0412, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8516, data[0], 0.001);
		assertEquals(0.3745, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2266, data[0], 0.001);
		assertEquals(0.7078, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7266, data[0], 0.001);
		assertEquals(0.1523, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4766, data[0], 0.001);
		assertEquals(0.4856, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9766, data[0], 0.001);
		assertEquals(0.8189, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0234, data[0], 0.001);
		assertEquals(0.2634, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5234, data[0], 0.001);
		assertEquals(0.5967, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2734, data[0], 0.001);
		assertEquals(0.9300, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7734, data[0], 0.001);
		assertEquals(0.0782, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1484, data[0], 0.001);
		assertEquals(0.4115, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6484, data[0], 0.001);
		assertEquals(0.7449, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3984, data[0], 0.001);
		assertEquals(0.1893, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8984, data[0], 0.001);
		assertEquals(0.5226, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0859, data[0], 0.001);
		assertEquals(0.8560, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5859, data[0], 0.001);
		assertEquals(0.3004, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3359, data[0], 0.001);
		assertEquals(0.6337, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8359, data[0], 0.001);
		assertEquals(0.9671, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2109, data[0], 0.001);
		assertEquals(0.0165, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7109, data[0], 0.001);
		assertEquals(0.3498, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4609, data[0], 0.001);
		assertEquals(0.6831, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9609, data[0], 0.001);
		assertEquals(0.1276, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0547, data[0], 0.001);
		assertEquals(0.4609, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5547, data[0], 0.001);
		assertEquals(0.7942, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3047, data[0], 0.001);
		assertEquals(0.2387, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8047, data[0], 0.001);
		assertEquals(0.5720, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1797, data[0], 0.001);
		assertEquals(0.9053, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6797, data[0], 0.001);
		assertEquals(0.0535, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4297, data[0], 0.001);
		assertEquals(0.3868, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9297, data[0], 0.001);
		assertEquals(0.7202, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1172, data[0], 0.001);
		assertEquals(0.1646, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6172, data[0], 0.001);
		assertEquals(0.4979, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3672, data[0], 0.001);
		assertEquals(0.8313, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8672, data[0], 0.001);
		assertEquals(0.2757, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2422, data[0], 0.001);
		assertEquals(0.6091, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7422, data[0], 0.001);
		assertEquals(0.9424, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4922, data[0], 0.001);
		assertEquals(0.0905, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9922, data[0], 0.001);
		assertEquals(0.4239, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0039, data[0], 0.001);
		assertEquals(0.7572, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5039, data[0], 0.001);
		assertEquals(0.2016, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2539, data[0], 0.001);
		assertEquals(0.5350, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7539, data[0], 0.001);
		assertEquals(0.8683, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1289, data[0], 0.001);
		assertEquals(0.3128, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6289, data[0], 0.001);
		assertEquals(0.6461, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3789, data[0], 0.001);
		assertEquals(0.9794, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8789, data[0], 0.001);
		assertEquals(0.0288, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0664, data[0], 0.001);
		assertEquals(0.3621, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5664, data[0], 0.001);
		assertEquals(0.6955, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3164, data[0], 0.001);
		assertEquals(0.1399, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8164, data[0], 0.001);
		assertEquals(0.4733, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1914, data[0], 0.001);
		assertEquals(0.8066, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6914, data[0], 0.001);
		assertEquals(0.2510, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4414, data[0], 0.001);
		assertEquals(0.5844, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9414, data[0], 0.001);
		assertEquals(0.9177, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0352, data[0], 0.001);
		assertEquals(0.0658, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5352, data[0], 0.001);
		assertEquals(0.3992, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2852, data[0], 0.001);
		assertEquals(0.7325, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7852, data[0], 0.001);
		assertEquals(0.1770, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1602, data[0], 0.001);
		assertEquals(0.5103, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6602, data[0], 0.001);
		assertEquals(0.8436, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4102, data[0], 0.001);
		assertEquals(0.2881, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9102, data[0], 0.001);
		assertEquals(0.6214, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0977, data[0], 0.001);
		assertEquals(0.9547, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5977, data[0], 0.001);
		assertEquals(0.1029, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3477, data[0], 0.001);
		assertEquals(0.4362, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8477, data[0], 0.001);
		assertEquals(0.7695, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2227, data[0], 0.001);
		assertEquals(0.2140, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7227, data[0], 0.001);
		assertEquals(0.5473, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4727, data[0], 0.001);
		assertEquals(0.8807, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9727, data[0], 0.001);
		assertEquals(0.3251, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0195, data[0], 0.001);
		assertEquals(0.6584, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5195, data[0], 0.001);
		assertEquals(0.9918, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2695, data[0], 0.001);
		assertEquals(0.0082, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7695, data[0], 0.001);
		assertEquals(0.3416, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1445, data[0], 0.001);
		assertEquals(0.6749, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6445, data[0], 0.001);
		assertEquals(0.1193, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3945, data[0], 0.001);
		assertEquals(0.4527, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8945, data[0], 0.001);
		assertEquals(0.7860, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0820, data[0], 0.001);
		assertEquals(0.2305, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5820, data[0], 0.001);
		assertEquals(0.5638, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3320, data[0], 0.001);
		assertEquals(0.8971, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8320, data[0], 0.001);
		assertEquals(0.0453, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2070, data[0], 0.001);
		assertEquals(0.3786, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7070, data[0], 0.001);
		assertEquals(0.7119, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4570, data[0], 0.001);
		assertEquals(0.1564, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9570, data[0], 0.001);
		assertEquals(0.4897, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0508, data[0], 0.001);
		assertEquals(0.8230, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5508, data[0], 0.001);
		assertEquals(0.2675, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3008, data[0], 0.001);
		assertEquals(0.6008, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8008, data[0], 0.001);
		assertEquals(0.9342, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1758, data[0], 0.001);
		assertEquals(0.0823, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6758, data[0], 0.001);
		assertEquals(0.4156, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4258, data[0], 0.001);
		assertEquals(0.7490, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9258, data[0], 0.001);
		assertEquals(0.1934, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1133, data[0], 0.001);
		assertEquals(0.5267, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6133, data[0], 0.001);
		assertEquals(0.8601, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3633, data[0], 0.001);
		assertEquals(0.3045, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8633, data[0], 0.001);
		assertEquals(0.6379, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2383, data[0], 0.001);
		assertEquals(0.9712, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7383, data[0], 0.001);
		assertEquals(0.0206, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4883, data[0], 0.001);
		assertEquals(0.3539, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9883, data[0], 0.001);
		assertEquals(0.6872, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0117, data[0], 0.001);
		assertEquals(0.1317, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5117, data[0], 0.001);
		assertEquals(0.4650, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2617, data[0], 0.001);
		assertEquals(0.7984, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7617, data[0], 0.001);
		assertEquals(0.2428, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1367, data[0], 0.001);
		assertEquals(0.5761, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6367, data[0], 0.001);
		assertEquals(0.9095, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3867, data[0], 0.001);
		assertEquals(0.0576, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8867, data[0], 0.001);
		assertEquals(0.3909, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0742, data[0], 0.001);
		assertEquals(0.7243, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5742, data[0], 0.001);
		assertEquals(0.1687, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3242, data[0], 0.001);
		assertEquals(0.5021, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8242, data[0], 0.001);
		assertEquals(0.8354, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1992, data[0], 0.001);
		assertEquals(0.2798, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6992, data[0], 0.001);
		assertEquals(0.6132, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4492, data[0], 0.001);
		assertEquals(0.9465, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9492, data[0], 0.001);
		assertEquals(0.0947, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0430, data[0], 0.001);
		assertEquals(0.4280, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5430, data[0], 0.001);
		assertEquals(0.7613, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2930, data[0], 0.001);
		assertEquals(0.2058, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7930, data[0], 0.001);
		assertEquals(0.5391, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1680, data[0], 0.001);
		assertEquals(0.8724, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6680, data[0], 0.001);
		assertEquals(0.3169, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4180, data[0], 0.001);
		assertEquals(0.6502, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9180, data[0], 0.001);
		assertEquals(0.9835, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1055, data[0], 0.001);
		assertEquals(0.0329, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6055, data[0], 0.001);
		assertEquals(0.3663, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3555, data[0], 0.001);
		assertEquals(0.6996, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8555, data[0], 0.001);
		assertEquals(0.1440, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2305, data[0], 0.001);
		assertEquals(0.4774, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7305, data[0], 0.001);
		assertEquals(0.8107, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4805, data[0], 0.001);
		assertEquals(0.2551, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9805, data[0], 0.001);
		assertEquals(0.5885, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0273, data[0], 0.001);
		assertEquals(0.9218, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5273, data[0], 0.001);
		assertEquals(0.0700, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2773, data[0], 0.001);
		assertEquals(0.4033, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7773, data[0], 0.001);
		assertEquals(0.7366, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1523, data[0], 0.001);
		assertEquals(0.1811, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6523, data[0], 0.001);
		assertEquals(0.5144, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4023, data[0], 0.001);
		assertEquals(0.8477, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9023, data[0], 0.001);
		assertEquals(0.2922, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0898, data[0], 0.001);
		assertEquals(0.6255, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5898, data[0], 0.001);
		assertEquals(0.9588, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3398, data[0], 0.001);
		assertEquals(0.1070, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8398, data[0], 0.001);
		assertEquals(0.4403, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2148, data[0], 0.001);
		assertEquals(0.7737, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7148, data[0], 0.001);
		assertEquals(0.2181, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4648, data[0], 0.001);
		assertEquals(0.5514, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9648, data[0], 0.001);
		assertEquals(0.8848, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.0586, data[0], 0.001);
		assertEquals(0.3292, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.5586, data[0], 0.001);
		assertEquals(0.6626, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3086, data[0], 0.001);
		assertEquals(0.9959, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8086, data[0], 0.001);
		assertEquals(0.0014, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1836, data[0], 0.001);
		assertEquals(0.3347, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6836, data[0], 0.001);
		assertEquals(0.6680, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4336, data[0], 0.001);
		assertEquals(0.1125, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9336, data[0], 0.001);
		assertEquals(0.4458, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.1211, data[0], 0.001);
		assertEquals(0.7791, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.6211, data[0], 0.001);
		assertEquals(0.2236, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.3711, data[0], 0.001);
		assertEquals(0.5569, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.8711, data[0], 0.001);
		assertEquals(0.8903, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.2461, data[0], 0.001);
		assertEquals(0.0384, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.7461, data[0], 0.001);
		assertEquals(0.3717, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.4961, data[0], 0.001);
		assertEquals(0.7051, data[1], 0.001);
		
		data = sequence.randDouble();
		assertEquals(0.9961, data[0], 0.001);
		assertEquals(0.1495, data[1], 0.001);
	}
}