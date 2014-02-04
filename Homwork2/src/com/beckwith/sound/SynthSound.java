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
			mc = synth.getChannels();
			Instrument[] instr = synth.getAvailableInstruments();
			synth.loadInstrument(instr[10]);
		
			
			mc[1].noteOn(39, 600);
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void playNote(int i){
		mc[1].noteOn(i, 600);
	}
	public void playChord(int i){
		mc[1].noteOn(i, 600);
		mc[2].noteOn(i+3,600);
		mc[3].noteOn(i+5,600);
	}
}
