/*
 * BoxController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NoteService;
import services.RefereeService;
import domain.Note;

@Controller
@RequestMapping("/note")
public class NoteController extends AbstractController {

	//Managed services
	@Autowired
	private RefereeService	refereeService;

	@Autowired
	private NoteService		noteService;


	// Constructors -----------------------------------------------------------

	public NoteController() {
		super();
	}

	// List ---------------------------------------------------------------		

	@RequestMapping(value = "referee,customer,handyworker/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int reportId) {
		ModelAndView result;
		Collection<Note> notes = null;

		notes = this.noteService.getNotesByReportId(reportId);

		result = new ModelAndView("note/noteList");

		result.addObject("notes", notes);
		result.addObject("requestURI", "note/referee,customer,handyworker/list.do");

		return result;
	}

	// Create ---------------------------------------------------------------		

	@RequestMapping(value = "referee,customer,handyworker/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int reportId) {
		final ModelAndView result;
		Note note;

		note = this.refereeService.writeNoteOfReportByRefereeCustomerHandy(reportId);
		result = this.createEditModelAndView(note, 1);
		result.addObject("toshow", false);
		return result;
	}

	// Save ---------------------------------------------------------------		
	@RequestMapping(value = "referee,customer,handyworker/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(note, 0);
		else
			try {
				this.refereeService.saveNoteOfReportByRefereeCustomerHandy(note);
				result = new ModelAndView("redirect:list.do?reportId=" + note.getReport().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error", 1);
			}

		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "referee,customer,handyworker/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId, @RequestParam final int edit) {
		final ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);
		Assert.notNull(note);

		result = this.createEditModelAndView(note, edit);

		boolean toShow;
		if (edit == 1)
			toShow = false;
		else
			toShow = true;

		result.addObject("toShow", toShow);
		return result;
	}
	// Ancillary Methods -----------------------------------------------------
	protected ModelAndView createEditModelAndView(final Note note, final int edit) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null, edit);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String messageCode, final int edit) {
		ModelAndView result;

		if (edit == 1) {
			result = new ModelAndView("note/noteEdit");
			if (note.getId() == 0)
				result = new ModelAndView("note/noteCreate");
		} else
			result = new ModelAndView("note/noteShow");

		result.addObject("note", note);
		result.addObject("message", messageCode);

		return result;
	}
}
