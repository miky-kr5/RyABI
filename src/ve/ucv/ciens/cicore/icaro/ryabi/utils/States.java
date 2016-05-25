/*
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * See the LICENSE file for more details.
 */

package ve.ucv.ciens.cicore.icaro.ryabi.utils;

import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.CatchBallBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.SearchBallBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.VictoryBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.WanderBehavior;

/**
 * This enum defines the valid robot states. The allowed states are as follows.
 * 
 * <ul>
 *   <li> WANDER: see {@link WanderBehavior}. </li>
 *   <li> SEARCH_BALL: see {@link SearchBallBehavior}. </li>
 *   <li> BALL_FOUND: see {@link CatchBallBehavior}. </li>
 *   <li> VICTORY: see {@link VictoryBehavior}. </li>
 *   <li> DONE: no behavior is active and the program ends. </li>
 * </ul>
 * 
 * @author Miguel Angel Astor Romero.
 */
public enum States {
	WANDER, SEARCH_BALL, BALL_FOUND, VICTORY, DONE;
}
