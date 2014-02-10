package com.beckwith.sound;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class SynthSound {
	private Synthesizer synth;
	private Instrument[] instr;
	private MidiChannel[] mc;
	
	public SynthSound(){
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			
			instr = synth.getAvailableInstruments();
			synth.loadInstrument(instr[36]);
			mc = synth.getChannels();
			
			
			//mc[1].programChange(0, instr[112].getPatch().getProgram());
			
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void playNote(int i){
		
		mc[1].noteOn(i, 600);
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
		}
		mc[1].noteOff(i);
	}
	public void playChord(int i){
		
		mc[1].noteOn(i, 600);
		mc[2].noteOn(i+5,600);
		mc[3].noteOn(i+10,600);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		
		mc[1].noteOff(i);
		mc[2].noteOff(i+5);
		mc[3].noteOff(i+10);

		
	
	}
	
	public void setInstrument(int channel, int instrument){
		mc[channel].programChange(0, instr[instrument].getPatch().getProgram());
	}
	
	public void listInstruments(){
		
		for(int i = 0; i < instr.length; i++){
			System.out.println(""+i+". " + instr[i].getName());
		}
	}
}
