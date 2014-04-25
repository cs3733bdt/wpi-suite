/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Bobby Drop Tables
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.user.observers;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.user.controllers.UpdateUserController;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;


	public class UpdateUserRequestObserver implements RequestObserver {
		/** We don't actually use the controller,
		 * in the defect tracker they use it to print
		 * error messages.
		 */
		private final UpdateUserController controller;
		
		/**
		 * Constructs an observer for updating user controller
		 * @param controller updateUserController to be observed
		 */
		public UpdateUserRequestObserver(UpdateUserController controller) {
			this.controller = controller;
		}
		
		/**
		 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
		 */
		@Override
		public void responseSuccess(IRequest iReq) {
			final ResponseModel response = iReq.getResponse();
			
			// The user that got updated
			User updatedUser = User.fromJSON(response.getBody());
			System.out.println("Updated User:" + updatedUser);
			
			System.out.println("The request to update a user has succeeded!");
		}
		
		/**
		 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
		 */
		@Override
		public void responseError(IRequest iReq) {
			System.err.println("Response Error: " + iReq.getResponse().getStatusMessage());
		}
		
		/**
		 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
		 */
		@Override
		public void fail(IRequest iReq, Exception exception) {
			System.err.println("The request to update a user failed with exception: "
					+ exception.getMessage());
		}
}

