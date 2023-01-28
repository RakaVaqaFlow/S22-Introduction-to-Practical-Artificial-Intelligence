#!/usr/bin/env python
# coding: utf-8

import music21
import random
from music21 import *
barbiegirl = converter.parse('barbiegirl_mono.mid')
input1 = converter.parse('input1.mid')
input2 = converter.parse('input2.mid')
input3 = converter.parse('input3.mid')

#get tonic of song
def getTonic(songFile):
    key = songFile.analyze('key')
    return key.tonic
#get mode of song ('minor' or 'major')
def getMode(songFile):
    key = songFile.analyze('key')
    return key.mode

#get notes of song
def getNotes(songFile):
    notes = []
    for i in songFile.recurse():
        if isinstance(i, note.Note):
            notes.append([i.pitch.midi, i.duration.quarterLength])
        if isinstance(i, note.Rest):
            notes.append([-1, i.duration.quarterLength])
    return notes

#generate tonics table with notes to construct chords
def generateTonicsTable(songFile):
    tonic = getTonic(songFile)
    mode = getMode(songFile)
    tonics = [tonic.midi]
    if(mode == 'minor'):
        offset = [2, 1, 2, 2, 1, 2, 2]
    else:
        offset = [2, 2, 1, 2, 2, 2, 1]
    
    for i in range(len(offset)-1):
        tonics.append(tonics[i]+offset[i])
    return tonics

#get info about song
def getInfоAboutSong(songFile):
    sum = 0
    for i in songFile.recurse():
        if isinstance(i, note.Note):
            print(f"info about note {i.pitch.midi} и {i.duration.quarterLength}")
            sum+=i.duration.quarterLength
        if isinstance(i, note.Rest):
            print(f"info about rest {i.duration.quarterLength}")
            sum+=i.duration.quarterLength
    print(sum) 
#generate chord to create an ideal target 
def generateChord(songFile, note):
    notesForChord = []
    notesForChord.append(note)
    it = 0
    tonicsTable = generateTonicsTable(songFile)
    while it<len(tonicsTable) and tonicsTable[it]!=note:
        it+=1
    notesForChord.append(tonicsTable[(it+2)%7])
    notesForChord.append(tonicsTable[(it+4)%7])
    return notesForChord

#creating a framework for our future accompaniment
def generateСhordsPosition(songFile):
    sum = 0 
    chords = [] 
    currentNote = 0
    startNote = -1
    for i in songFile.recurse():
        if isinstance(i, note.Note) or isinstance(i, note.Rest): 
            if sum == 0 and startNote == -1:
                startNote = i
            sum += i.duration.quarterLength
            if sum >= 1:
                while sum > 0:
                    sum-=1
                    if isinstance(startNote, note.Note):
                        chords.append(startNote.pitch.midi)
                    else:
                        chords.append(-1)
                startNote = -1
    for i in range(len(chords)):
        if chords[i]==-1:
            it = i
            while chords[it]==-1:
                it+=1
            while it!=i:
                chords[i]=chords[it]
                i+=1
    return chords
chordsPosition = generateСhordsPosition(input1)

# Number of individuals in each generation
POPULATION_SIZE = 100
 
# Valid genes
GENES = range(0, 127)
    
#create random genes for mutation
def mutated_genes():
    return random.choice(GENES)
    
#create chromosome or chords of genes
def create_gnome():
    return [mutated_genes() for _ in range(3)]
 
#Perform mating and produce new offspring
def mate(par1, par2):
 
    # chromosome for offspring
    child_chromosome = []
    for gp1, gp2 in zip(par1.chromosome, par2.chromosome):   
 
        # random probability 
        prob = random.random()
 
        # if prob is less than 0.45, insert gene
        # from parent 1
        if prob < 0.45:
            child_chromosome.append(gp1)
 
        # if prob is between 0.45 and 0.90, insert
        # gene from parent 2
        elif prob < 0.90:
            child_chromosome.append(gp2)
 
        # otherwise insert random gene(mutate),
        # for maintaining diversity
        else:
            child_chromosome.append(par1.mutated_genes())
 
    # create new Individual(offspring) using
    # generated chromosome for offspring
    return child_chromosome

#Calculate fitness score, it is the number of
#characters in string which differ from target
#string.
def calc_fitness(target, gnome):
    fitness = abs(target[0]-gnome[0])+abs(target[1]-gnome[1])+abs(target[2]-gnome[2])
    return fitness

#genetic algorithm to generate chord for accompaniment
def generateGeneticChord(target):
    #current generation
    generation = 1
    found = False
    population = []
    for _ in range(POPULATION_SIZE):
                    gnome = create_gnome()
                    population.append(gnome)
    while not found or generation!=1000:
        # sort the population in increasing order of fitness score
        population = sorted(population, key=lambda x: calc_fitness(target, x))

        # if the individual having lowest fitness score ie.
        # 0 then we know that we have reached to the target
        # and break the loop
        if calc_fitness(population[0]) <= 0:
            found = True
            return population[0]
            break

        # Otherwise generate new offsprings for new generation
        new_generation = []

        # Perform Elitism, that mean 10% of fittest population
        # goes to the next generation
        s = int((10 * POPULATION_SIZE) / 100)
        new_generation.extend(population[:s])

        # From 50% of fittest population, Individuals
        # will mate to produce offspring
        s = int((90 * POPULATION_SIZE) / 100)
        for _ in range(s):
            parent1 = random.choice(population[:50])
            parent2 = random.choice(population[:50])
            child = parent1.mate(parent2)
            new_generation.append(child)

        population = new_generation
        generation += 1
    return population[0]


#generate accompaniment for song with using genetic algorithm
def generateAccompaniment(songFile):            
    tempo_number = songFile.recurse().getElementsByClass(tempo.MetronomeMark)[0].number   
    accompaniment = stream.Part()
    accompaniment.append(meter.TimeSignature('4/4'))
    accompaniment.append(tempo.MetronomeMark(tempo_number))
    tonicsTable = generateTonicsTable(songFile)

    for i in chordsPosition:
        notesForChord = generateChord(songFile, i)
        geneticChord = generateGeneticChord(notesForChord)
        #print(notesForChord)
        accompaniment.append(chord.Chord(geneticChord))
    return accompaniment

#results
m1 = midi.translate.streamToMidiFile(input1)
m2 = midi.translate.streamToMidiFile(generateAccompaniment(input1))
m1.tracks.append(m2.tracks[1])
res1 = midi.translate.midiFileToStream(m1)
res1.show('midi')

m3 = midi.translate.streamToMidiFile(input2)
m4 = midi.translate.streamToMidiFile(generateAccompaniment(input2))
m3.tracks.append(m4.tracks[1])

res2 = midi.translate.midiFileToStream(m3)
res2.show('midi')

m5 = midi.translate.streamToMidiFile(input3)
m6 = midi.translate.streamToMidiFile(generateAccompaniment(input3))

m5.tracks.append(m6.tracks[1])

res3 = midi.translate.midiFileToStream(m5)
res3.show('midi')

fp1 = res1.write('midi', fp1='output1.mid')
fp2 = res2.write('midi', fp2='output2.mid')
fp3 = res3.write('midi', fp3='output3.mid')

